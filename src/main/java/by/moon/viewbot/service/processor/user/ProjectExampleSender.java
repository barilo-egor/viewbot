package by.moon.viewbot.service.processor.user;

import by.moon.viewbot.bean.ExampleProject;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.InlineType;
import by.moon.viewbot.service.beanservice.ExampleProjectService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class ProjectExampleSender {
    private MessageSender messageSender;
    private ExampleProjectService exampleProjectService;

    public void send(long chatId, CallbackQuery query){
        ExampleProject exampleProject =
                exampleProjectService.findById(Long.parseLong(query.getData().split(":")[1]));
        String text = "Название проекта: " + exampleProject.getName() + "\nСсылка: " + exampleProject.getLink() +
                "\n\n" + exampleProject.getDescription();
        messageSender.sendEditedMessage(chatId, query.getMessage().getMessageId(),
                text, KeyboardFactory.getInlineOneRow(
                        new String[]{"Вернуться к проектам"},
                        new String[]{Command.BACK_TO_OUR_PROJECT.getCommand()},
                        InlineType.DATA
                ));
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
