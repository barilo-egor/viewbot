package by.moon.viewbot.service.dispatchers;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.enums.UpdateType;
import by.moon.viewbot.exceptions.UserNotFoundException;
import by.moon.viewbot.service.beanservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateDispatcher {
    private UserService userService;

    private AdminCallbackQueryDispatcher adminCallbackQueryDispatcher;
    private AdminMessageDispatcher adminMessageDispatcher;
    private UserCallbackQueryDispatcher userCallbackQueryDispatcher;
    private UserMessageDispatcher userMessageDispatcher;

    public void dispatch(Update update){
        User user;
        switch (UpdateType.getUpdateType(update)){
            case MESSAGE:
                user = getUser(update.getMessage().getChat());
                switch (user.getRole()){
                    case USER:
                        userMessageDispatcher.dispatch(update.getMessage(), user);
                        break;
                    case ADMIN:
                        adminMessageDispatcher.dispatch(update.getMessage(), user);
                        break;
                }
                break;
            case CALLBACK_QUERY:
                user = getUser(update.getCallbackQuery().getMessage().getChat());
                switch (user.getRole()){
                    case USER:
                        userCallbackQueryDispatcher.dispatch(update.getCallbackQuery(), user);
                        break;
                    case ADMIN:
                        adminCallbackQueryDispatcher.dispatch(update.getCallbackQuery(), user);
                        break;
                }
                break;
        }
    }

    private User getUser(Chat chat) {
        try {
            return userService.findByChatId(chat.getId());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return userService.create(chat);
        }
    }

    @Autowired
    public void setAdminCallbackQueryDispatcher(AdminCallbackQueryDispatcher adminCallbackQueryDispatcher) {
        this.adminCallbackQueryDispatcher = adminCallbackQueryDispatcher;
    }

    @Autowired
    public void setAdminMessageDispatcher(AdminMessageDispatcher adminMessageDispatcher) {
        this.adminMessageDispatcher = adminMessageDispatcher;
    }

    @Autowired
    public void setUserCallbackQueryDispatcher(UserCallbackQueryDispatcher userCallbackQueryDispatcher) {
        this.userCallbackQueryDispatcher = userCallbackQueryDispatcher;
    }

    @Autowired
    public void setUserMessageDispatcher(UserMessageDispatcher userMessageDispatcher) {
        this.userMessageDispatcher = userMessageDispatcher;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
