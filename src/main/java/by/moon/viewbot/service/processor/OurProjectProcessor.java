package by.moon.viewbot.service.processor;

import by.moon.viewbot.bean.ExampleProject;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.InlineType;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.beanservice.ExampleProjectService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OurProjectProcessor {
    private MessageSender messageSender;
    private ExampleProjectService exampleProjectService;

    public void run(User user, String inputData, SystemMessage systemMessage){
        List<ExampleProject> exampleProjects = exampleProjectService.findAll();
        if(exampleProjects.size() == 0){
            messageSender.sendMessage(user.getChatId(), SystemMessage.EMPTY.getSystemMessage());
            return;
        }
        String[] projectNames = new String[exampleProjects.size()];
        String[] data = new String[exampleProjects.size()];
        int i = 0;
        for(ExampleProject exampleProject : exampleProjects){
            projectNames[i] = exampleProject.getName();
            data[i] = inputData + ":" + exampleProject.getId();
            i++;
        }
        messageSender.sendMessage(user.getChatId(), systemMessage.getSystemMessage(),
                KeyboardFactory.getInlineOneRow(projectNames, data, InlineType.DATA));
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setExampleProjectService(ExampleProjectService exampleProjectService) {
        this.exampleProjectService = exampleProjectService;
    }
}
