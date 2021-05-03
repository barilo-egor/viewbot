package by.moon.viewbot.service.processor.admin.menu;

import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.AdminMenuType;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMenuSender {
    private MessageSender messageSender;

    public void send(long chatId, AdminMenuType adminMenuType) {
        switch (adminMenuType) {
            case MAIN:
                messageSender.sendMessage(chatId, SystemMessage.ADMIN_MENU.getSystemMessage(),
                        KeyboardFactory.getReplyTwoRow(
                                Command.NEW_REQUESTS.getCommand(),
                                Command.CHANGE_USER_MENU_MESSAGE.getCommand(),
                                Command.CHANGE_GREETING.getCommand(),
                                Command.CHANGE_ABOUT_US.getCommand(),
                                Command.CHANGE_CONNECT_WITH_MANAGER.getCommand(),
                                Command.BOT_BENEFIT_EDIT_MENU.getCommand(),
                                Command.OUR_PROJECTS_EDIT_MENU.getCommand(),
                                Command.OUT_OF_ADMIN.getCommand()));
                break;
            case BOT_BENEFIT:
                messageSender.sendMessage(chatId, SystemMessage.ADMIN_BOT_BENEFIT_MENU.getSystemMessage(),
                KeyboardFactory.getReplyTwoRow(Command.ADD_FREQUENTLY_QUESTION.getCommand(),
                        Command.DELETE_FREQUENTLY_QUESTION.getCommand(),
                        Command.BACK_TO_ADMIN_MENU.getCommand()));
                break;
            case OUR_PROJECTS:
                messageSender.sendMessage(chatId, SystemMessage.OUR_PROJECTS_MENU.getSystemMessage(),
                        KeyboardFactory.getReplyTwoRow(Command.ADD_OUR_PROJECT.getCommand(),
                                Command.DELETE_OUR_PROJECT.getCommand(),
                                Command.BACK_TO_ADMIN_MENU.getCommand()));
                break;
        }
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
