package by.moon.viewbot.service.dispatchers;

import by.moon.viewbot.bean.BotMessage;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.*;
import by.moon.viewbot.service.beanservice.BotMessageService;
import by.moon.viewbot.service.beanservice.UserService;
import by.moon.viewbot.service.processor.BotBenefitProcessor;
import by.moon.viewbot.service.processor.OurProjectProcessor;
import by.moon.viewbot.service.processor.admin.menu.AdminMenuSender;
import by.moon.viewbot.service.processor.user.GreetingSender;
import by.moon.viewbot.service.processor.user.RequestRegistrationProcessor;
import by.moon.viewbot.service.processor.user.menu.UserMenuSender;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UserMessageDispatcher {
    private UserMenuSender userMenuSender;
    private GreetingSender greetingSender;
    private MessageSender messageSender;
    private BotMessageService botMessageService;
    private UserService userService;
    private AdminMenuSender adminMenuSender;
    private BotBenefitProcessor botBenefitProcessor;
    private OurProjectProcessor ourProjectProcessor;
    private RequestRegistrationProcessor requestRegistrationProcessor;

    public void dispatch(Message message, User user){
        if(user.getCurrentStep() != CurrentStep.START) dispatchByCurrentCommand(message, user);
        else dispatchByNewCommand(message, user);
    }

    private void dispatchByNewCommand(Message message, User user) {
        Command command = Command.fromString(message.getText());
        switch (command){
            default:
                if(!isAdminPassword(message, user)) {
                    userMenuSender.send(user.getChatId(), UserMenuType.MAIN);
                } else {
                    adminMenuSender.send(user.getChatId(), AdminMenuType.MAIN);
                }
                break;
            case START:
                greetingSender.send(user.getChatId());
                userMenuSender.send(user.getChatId(), UserMenuType.MAIN);
                break;
            case ABOUT_US:
            case CONNECT_WITH_MANAGER:
                BotMessage botMessage;
                switch (command){
                    case ABOUT_US:
                        botMessage = botMessageService.findByType(BotMessageType.ABOUT_US);
                        break;
                    case CONNECT_WITH_MANAGER:
                        botMessage = botMessageService.findByType(BotMessageType.CONNECT_WITH_MANAGER);
                        break;
                    default:
                        botMessage = botMessageService.findByType(BotMessageType.ERROR);
                        break;
                }
                messageSender.sendMessage(user.getChatId(), botMessage.getText());
                break;
            case FAQ_BOT:
                botBenefitProcessor.run(user, Command.READ_FREQ_QUESTION.getCommand(),
                        SystemMessage.USER_BOT_BENEFIT_MENU);
                break;
            case OUR_PROJECTS:
                ourProjectProcessor.run(user, Command.READ_OUR_PROJECT.getCommand(), SystemMessage.READ_OUR_PROJECT);
                break;
            case WANT_BOT:
                user.setCurrentStep(CurrentStep.ENTERING_DESCRIPTION_FOR_REQUEST);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_DESCRIPTION_FOR_REQUEST.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
        }
    }

    private boolean isAdminPassword(Message message, User user) {
        if(message.getText().equals(Command.PASSWORD.getCommand())){
            user.setRole(Role.ADMIN);
            userService.save(user);
            return true;
        } else return false;
    }

    private void dispatchByCurrentCommand(Message message, User user) {
        if(message.hasText() && message.getText().equals(Command.CANCEL.getCommand())) {
            processCancel(user);
            return;
        }
        switch (user.getCurrentStep()){
            case ENTERING_DESCRIPTION_FOR_REQUEST:
                requestRegistrationProcessor.saveDescriptionToBuffer(user, message);
                messageSender.sendMessage(user.getChatId(), SystemMessage.SEND_CONTACT_FOR_REQUEST.getSystemMessage(),
                        KeyboardFactory.getReplyContact("Отправить контакт", Command.CANCEL.getCommand()));
                break;
            case SENDING_CONTACT_FOR_REQUEST:
                requestRegistrationProcessor.createProjectRequest(user, message);
                messageSender.sendMessage(user.getChatId(), SystemMessage.REQUEST_CREATED.getSystemMessage());
                userMenuSender.send(user.getChatId(), UserMenuType.MAIN);
                break;
        }
    }

    private void processCancel(User user) {
        user.setCurrentStep(CurrentStep.START);
        userService.save(user);
        switch (user.getCurrentStep()){
            case ENTERING_DESCRIPTION_FOR_REQUEST:
            case SENDING_CONTACT_FOR_REQUEST:
                userMenuSender.send(user.getChatId(), UserMenuType.MAIN);
                break;
        }
    }

    @Autowired
    public void setRequestRegistrationProcessor(RequestRegistrationProcessor requestRegistrationProcessor) {
        this.requestRegistrationProcessor = requestRegistrationProcessor;
    }

    @Autowired
    public void setOurProjectProcessor(OurProjectProcessor ourProjectProcessor) {
        this.ourProjectProcessor = ourProjectProcessor;
    }

    @Autowired
    public void setBotBenefitProcessor(BotBenefitProcessor botBenefitProcessor) {
        this.botBenefitProcessor = botBenefitProcessor;
    }

    @Autowired
    public void setAdminMenuSender(AdminMenuSender adminMenuSender) {
        this.adminMenuSender = adminMenuSender;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBotMessageService(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setGreetingSender(GreetingSender greetingSender) {
        this.greetingSender = greetingSender;
    }

    @Autowired
    public void setUserMenuSender(UserMenuSender userMenuSender) {
        this.userMenuSender = userMenuSender;
    }
}
