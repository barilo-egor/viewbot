package by.moon.viewbot.service.dispatchers;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.*;
import by.moon.viewbot.service.beanservice.UserService;
import by.moon.viewbot.service.processor.OurProjectProcessor;
import by.moon.viewbot.service.processor.admin.AddOurProjectProcessor;
import by.moon.viewbot.service.processor.admin.ChangeBotMessageProcessor;
import by.moon.viewbot.service.processor.admin.NewRequestsProcessor;
import by.moon.viewbot.service.processor.admin.menu.AdminMenuSender;
import by.moon.viewbot.service.processor.admin.AddNewFrequentlyQuestionProcessor;
import by.moon.viewbot.service.processor.BotBenefitProcessor;
import by.moon.viewbot.service.processor.user.menu.UserMenuSender;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class AdminMessageDispatcher {
    private AdminMenuSender adminMenuSender;
    private UserService userService;
    private UserMenuSender userMenuSender;
    private MessageSender messageSender;
    private AddNewFrequentlyQuestionProcessor addNewFrequentlyQuestionProcessor;
    private BotBenefitProcessor botBenefitProcessor;
    private ChangeBotMessageProcessor changeBotMessageProcessor;
    private AddOurProjectProcessor addOurProjectProcessor;
    private OurProjectProcessor ourProjectProcessor;
    private NewRequestsProcessor newRequestsProcessor;

    public void dispatch(Message message, User user){
        if(user.getCurrentStep() != CurrentStep.START) dispatchByCurrentCommand(message, user);
        else dispatchByNewCommand(message, user);
    }

    private void dispatchByNewCommand(Message message, User user) {
        Command command = Command.fromString(message.getText());
        switch (command){
            case BACK_TO_ADMIN_MENU:
            default:
                adminMenuSender.send(user.getChatId(), AdminMenuType.MAIN);
                break;
            case OUT_OF_ADMIN:
                user.setRole(Role.USER);
                userService.save(user);
                userMenuSender.send(user.getChatId(), UserMenuType.MAIN);
                break;
            case BOT_BENEFIT_EDIT_MENU:
                adminMenuSender.send(user.getChatId(), AdminMenuType.BOT_BENEFIT);
                break;
            case ADD_FREQUENTLY_QUESTION:
                user.setCurrentStep(CurrentStep.ADD_QUESTION_FOR_NEW_FREQ_QUESTION);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NEW_QUESTION.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
            case DELETE_FREQUENTLY_QUESTION:
                botBenefitProcessor.run(user, Command.DELETE_FREQ_QUESTION.getCommand(),
                        SystemMessage.CHOOSE_FREQ_QUESTION_FOR_DELETE);
                break;
            case CHANGE_ABOUT_US:
                user.setCurrentStep(CurrentStep.ENTERING_NEW_ABOUT_US);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NEW_ABOUT_US.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
            case CHANGE_GREETING:
                user.setCurrentStep(CurrentStep.ENTERING_NEW_GREETING);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NEW_GREETING.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
            case CHANGE_USER_MENU_MESSAGE:
                user.setCurrentStep(CurrentStep.ENTER_USER_MENU_MESSAGE);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NEW_USER_MENU_MESSAGE.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
            case CHANGE_CONNECT_WITH_MANAGER:
                user.setCurrentStep(CurrentStep.ENTERING_NEW_CONNECT_WITH_MANAGER);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NEW_CONNECT_WITH_MANAGER.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
            case OUR_PROJECTS_EDIT_MENU:
                adminMenuSender.send(user.getChatId(), AdminMenuType.OUR_PROJECTS);
                break;
            case ADD_OUR_PROJECT:
                user.setCurrentStep(CurrentStep.ENTERING_NAME_FOR_OUR_PROJECT);
                userService.save(user);
                messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NAME_FOR_OUR_PROJECT.getSystemMessage(),
                        KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
                break;
            case DELETE_OUR_PROJECT:
                ourProjectProcessor.run(user, Command.DELETE_OUR_PROJECT.getCommand(),
                        SystemMessage.CHOOSE_OUR_PROJECT_FOR_DELETE);
                break;
            case NEW_REQUESTS:
                newRequestsProcessor.run(user);
                break;
        }
    }

    private void processCancel(User user) {
        user.setCurrentStep(CurrentStep.START);
        userService.save(user);
        switch (user.getCurrentStep()){
            case ADD_QUESTION_FOR_NEW_FREQ_QUESTION:
            case ADD_ANSWER_FOR_NEW_FREQ_QUESTION:
                adminMenuSender.send(user.getChatId(), AdminMenuType.BOT_BENEFIT);
                break;
            case ENTERING_NAME_FOR_OUR_PROJECT:
            case ENTERING_LINK_FOR_OUR_PROJECT:
            case ENTERING_DESCRIPTION_FOR_OUR_PROJECT:
                adminMenuSender.send(user.getChatId(), AdminMenuType.OUR_PROJECTS);
                break;
            case ENTERING_NEW_ABOUT_US:
            case ENTERING_NEW_GREETING:
            case ENTERING_NEW_CONNECT_WITH_MANAGER:
                adminMenuSender.send(user.getChatId(), AdminMenuType.MAIN);
                break;
        }
    }

    private void dispatchByCurrentCommand(Message message, User user) {
        if(message.getText().equals(Command.CANCEL.getCommand())) {
            processCancel(user);
            return;
        }
        switch (user.getCurrentStep()){
            case ADD_QUESTION_FOR_NEW_FREQ_QUESTION:
                addNewFrequentlyQuestionProcessor.saveQuestionTextToBuffer(user, message);
                break;
            case ADD_ANSWER_FOR_NEW_FREQ_QUESTION:
                addNewFrequentlyQuestionProcessor.createFrequentlyQuestion(user, message);
                adminMenuSender.send(user.getChatId(), AdminMenuType.BOT_BENEFIT);
                break;
            case ENTERING_NEW_ABOUT_US:
            case ENTERING_NEW_CONNECT_WITH_MANAGER:
            case ENTERING_NEW_GREETING:
            case ENTER_USER_MENU_MESSAGE:
                changeBotMessageProcessor.change(user, message);
                break;
            case ENTERING_NAME_FOR_OUR_PROJECT:
                addOurProjectProcessor.saveNameToBuffer(user, message);
                messageSender.sendMessage(user.getChatId(),
                        SystemMessage.ENTER_LINK_FOR_OUR_PROJECT.getSystemMessage());
                break;
            case ENTERING_LINK_FOR_OUR_PROJECT:
                addOurProjectProcessor.saveLinkToBuffer(user, message);
                messageSender.sendMessage(user.getChatId(),
                        SystemMessage.ENTER_DESCRIPTION_FOR_OUR_PROJECT.getSystemMessage());
                break;
            case ENTERING_DESCRIPTION_FOR_OUR_PROJECT:
                addOurProjectProcessor.createOurProject(user, message);
                messageSender.sendMessage(user.getChatId(), SystemMessage.SAVED.getSystemMessage());
                adminMenuSender.send(user.getChatId(), AdminMenuType.OUR_PROJECTS);
                break;
        }
    }

    @Autowired
    public void setNewRequestsProcessor(NewRequestsProcessor newRequestsProcessor) {
        this.newRequestsProcessor = newRequestsProcessor;
    }

    @Autowired
    public void setOurProjectProcessor(OurProjectProcessor ourProjectProcessor) {
        this.ourProjectProcessor = ourProjectProcessor;
    }

    @Autowired
    public void setAddOurProjectProcessor(AddOurProjectProcessor addOurProjectProcessor) {
        this.addOurProjectProcessor = addOurProjectProcessor;
    }

    @Autowired
    public void setChangeBotMessageProcessor(ChangeBotMessageProcessor changeBotMessageProcessor) {
        this.changeBotMessageProcessor = changeBotMessageProcessor;
    }

    @Autowired
    public void setBotBenefitProcessor(BotBenefitProcessor botBenefitProcessor) {
        this.botBenefitProcessor = botBenefitProcessor;
    }

    @Autowired
    public void setAddNewFrequentlyQuestionProcessor(AddNewFrequentlyQuestionProcessor addNewFrequentlyQuestionProcessor) {
        this.addNewFrequentlyQuestionProcessor = addNewFrequentlyQuestionProcessor;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setUserMenuSender(UserMenuSender userMenuSender) {
        this.userMenuSender = userMenuSender;
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
