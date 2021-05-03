package by.moon.viewbot.service.processor.admin;

import by.moon.viewbot.bean.ExampleProject;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.enums.CurrentStep;
import by.moon.viewbot.service.beanservice.ExampleProjectService;
import by.moon.viewbot.service.beanservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class AddOurProjectProcessor {
    private UserService userService;
    private ExampleProjectService exampleProjectService;

    public void saveNameToBuffer(User user, Message message){
        user.setBuffer(message.getText());
        user.setCurrentStep(CurrentStep.ENTERING_LINK_FOR_OUR_PROJECT);
        userService.save(user);
    }

    public void saveLinkToBuffer(User user, Message message){
        user.setCurrentStep(CurrentStep.ENTERING_DESCRIPTION_FOR_OUR_PROJECT);
        user.setBuffer(user.getBuffer() + ":" + message.getText());
        userService.save(user);
    }

    public void createOurProject(User user, Message message){
        ExampleProject exampleProject = new ExampleProject();
        exampleProject.setName(user.getBuffer().split(":")[0]);
        exampleProject.setLink(user.getBuffer().split(":")[1]);
        exampleProject.setDescription(message.getText());
        exampleProjectService.save(exampleProject);
        user.setCurrentStep(CurrentStep.START);
        userService.save(user);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setExampleProjectService(ExampleProjectService exampleProjectService) {
        this.exampleProjectService = exampleProjectService;
    }
}
