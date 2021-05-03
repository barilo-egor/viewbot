package by.moon.viewbot.service.processor.admin;

import by.moon.viewbot.bean.ProjectRequest;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.InlineType;
import by.moon.viewbot.service.beanservice.ProjectRequestService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewRequestsProcessor {
    private ProjectRequestService projectRequestService;
    private MessageSender messageSender;

    public void run(User user){
        List<ProjectRequest> activeRequests = projectRequestService.findAll().stream()
                .filter(ProjectRequest::getIsActive).collect(Collectors.toList());
        for(ProjectRequest request : activeRequests){
            String text = "Заявка №" + request.getId()
                    + "\n\nИмя: " + request.getFirstName() + " " + request.getLastName()
                    + "\nТелефон: " + request.getPhoneNumber()
                    +"\n\nОписание от пользователя:\n" + request.getDescription();
            messageSender.sendMessage(user.getChatId(), text,
                    KeyboardFactory.getInlineOneRow(
                            new String[] {"Скрыть"},
                            new String[] {Command.HIDE_REQUEST.getCommand() + ":" + request.getId()},
                            InlineType.DATA
                    ));
        }
    }

    public void hideRequest(User user, CallbackQuery query){
        ProjectRequest projectRequest =
                projectRequestService.findById(Long.parseLong(query.getData().split(":")[1]));
        projectRequest.setIsActive(false);
        projectRequestService.save(projectRequest);
        messageSender.deleteMessage(user.getChatId(), query.getMessage().getMessageId());
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setProjectRequestService(ProjectRequestService projectRequestService) {
        this.projectRequestService = projectRequestService;
    }
}
