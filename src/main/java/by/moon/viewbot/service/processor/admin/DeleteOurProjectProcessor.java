package by.moon.viewbot.service.processor.admin;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.beanservice.ExampleProjectService;
import by.moon.viewbot.service.processor.OurProjectProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class DeleteOurProjectProcessor {
    private MessageSender messageSender;
    private OurProjectProcessor ourProjectProcessor;
    private ExampleProjectService exampleProjectService;

    public void run(User user, CallbackQuery query){
        messageSender.deleteMessage(user.getChatId(), query.getMessage().getMessageId());
        exampleProjectService.delete(exampleProjectService
                .findById(Long.parseLong(query.getData().split(":")[1])));
        ourProjectProcessor.run(user, Command.DELETE_FREQ_QUESTION.getCommand(),
                SystemMessage.CHOOSE_OUR_PROJECT_FOR_DELETE);
    }

    @Autowired
    public void setOurProjectProcessor(OurProjectProcessor ourProjectProcessor) {
        this.ourProjectProcessor = ourProjectProcessor;
    }

    @Autowired
    public void setExampleProjectService(ExampleProjectService exampleProjectService) {
        this.exampleProjectService = exampleProjectService;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
