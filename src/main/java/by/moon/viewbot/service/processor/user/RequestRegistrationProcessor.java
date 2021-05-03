package by.moon.viewbot.service.processor.user;

import by.moon.viewbot.bean.ProjectRequest;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.CurrentStep;
import by.moon.viewbot.enums.Role;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.beanservice.ProjectRequestService;
import by.moon.viewbot.service.beanservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.stream.Collectors;

@Service
public class RequestRegistrationProcessor {
    private ProjectRequestService projectRequestService;
    private UserService userService;
    private MessageSender messageSender;

    public void saveDescriptionToBuffer(User user, Message message){
        user.setBuffer(message.getText());
        user.setCurrentStep(CurrentStep.SENDING_CONTACT_FOR_REQUEST);
        userService.save(user);
    }

    public void createProjectRequest(User user, Message message){
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setDescription(user.getBuffer());
        projectRequest.setFirstName(message.getContact().getFirstName());
        projectRequest.setLastName(message.getContact().getLastName());
        projectRequest.setPhoneNumber(message.getContact().getPhoneNumber());
        projectRequest.setIsActive(true);
        projectRequestService.save(projectRequest);
        user.setCurrentStep(CurrentStep.START);
        userService.save(user);

        noticeAdmins();
    }

    private void noticeAdmins() {
        for(User admin : userService.findAll().stream()
                .filter(user -> user.getRole().equals(Role.ADMIN)).collect(Collectors.toList())){
            messageSender.sendMessage(admin.getChatId(), SystemMessage.NEW_REQUEST.getSystemMessage());
        }
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProjectRequestService(ProjectRequestService projectRequestService) {
        this.projectRequestService = projectRequestService;
    }
}
