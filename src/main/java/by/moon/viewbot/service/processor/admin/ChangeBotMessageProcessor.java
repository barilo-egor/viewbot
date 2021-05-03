package by.moon.viewbot.service.processor.admin;

import by.moon.viewbot.bean.BotMessage;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.AdminMenuType;
import by.moon.viewbot.enums.BotMessageType;
import by.moon.viewbot.enums.CurrentStep;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.exceptions.StepForChangingNotFoundException;
import by.moon.viewbot.service.beanservice.BotMessageService;
import by.moon.viewbot.service.beanservice.UserService;
import by.moon.viewbot.service.processor.admin.menu.AdminMenuSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ChangeBotMessageProcessor {
    private BotMessageService botMessageService;
    private AdminMenuSender adminMenuSender;
    private UserService userService;
    private MessageSender messageSender;

    public void change(User user, Message message){
        BotMessage botMessage;
        switch (user.getCurrentStep()){
            case ENTERING_NEW_ABOUT_US:
                botMessage = botMessageService.findByType(BotMessageType.ABOUT_US);
                break;
            case ENTERING_NEW_CONNECT_WITH_MANAGER:
                botMessage = botMessageService.findByType(BotMessageType.CONNECT_WITH_MANAGER);
                break;
            case ENTERING_NEW_GREETING:
                botMessage = botMessageService.findByType(BotMessageType.GREETING);
                break;
            case ENTER_USER_MENU_MESSAGE:
                botMessage = botMessageService.findByType(BotMessageType.USER_MAIN_MENU);
                break;
            default:
                throw new StepForChangingNotFoundException();
        }
        botMessage.setText(message.getText());
        botMessageService.save(botMessage);
        user.setCurrentStep(CurrentStep.START);
        userService.save(user);
        messageSender.sendMessage(user.getChatId(), SystemMessage.SAVED.getSystemMessage());
        adminMenuSender.send(user.getChatId(), AdminMenuType.MAIN);
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setBotMessageService(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Autowired
    public void setAdminMenuSender(AdminMenuSender adminMenuSender) {
        this.adminMenuSender = adminMenuSender;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
