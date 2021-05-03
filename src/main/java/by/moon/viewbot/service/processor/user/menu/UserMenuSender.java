package by.moon.viewbot.service.processor.user.menu;

import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.BotMessageType;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.UserMenuType;
import by.moon.viewbot.service.beanservice.BotMessageService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMenuSender {
    private MessageSender messageSender;
    private BotMessageService botMessageService;

    public void send(long chatId, UserMenuType userMenuType) {
        switch (userMenuType) {
            case MAIN:
                messageSender.sendMessage(chatId, botMessageService.findByType(BotMessageType.USER_MAIN_MENU).getText(),
                        KeyboardFactory.getReplyTwoRow(
                                Command.FAQ_BOT.getCommand(),
                                Command.ABOUT_US.getCommand(),
                                Command.OUR_PROJECTS.getCommand(),
                                Command.CONNECT_WITH_MANAGER.getCommand(),
                                Command.WANT_BOT.getCommand()));
                break;
        }
    }

    @Autowired
    public void setBotMessageService(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
