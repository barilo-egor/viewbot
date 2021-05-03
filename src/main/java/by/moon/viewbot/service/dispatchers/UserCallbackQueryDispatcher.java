package by.moon.viewbot.service.dispatchers;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.processor.BotBenefitProcessor;
import by.moon.viewbot.service.processor.OurProjectProcessor;
import by.moon.viewbot.service.processor.user.FreqQuestionAnswerSender;
import by.moon.viewbot.service.processor.user.ProjectExampleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class UserCallbackQueryDispatcher {
    private FreqQuestionAnswerSender freqQuestionAnswerSender;
    private BotBenefitProcessor botBenefitProcessor;
    private MessageSender messageSender;
    private ProjectExampleSender projectExampleSender;
    private OurProjectProcessor ourProjectProcessor;

    public void dispatch(CallbackQuery callbackQuery, User user){
        switch (Command.fromString(callbackQuery.getData())){
            default:
                if(callbackQuery.getData().startsWith(Command.READ_FREQ_QUESTION.getCommand())){
                    freqQuestionAnswerSender.send(user.getChatId(), callbackQuery);
                } else if(callbackQuery.getData().startsWith(Command.READ_OUR_PROJECT.getCommand())){
                    projectExampleSender.send(user.getChatId(), callbackQuery);
                }
                break;
            case BACK_TO_BOT_BENEFIT:
                messageSender.deleteMessage(user.getChatId(), callbackQuery.getMessage().getMessageId());
                botBenefitProcessor.run(user, Command.READ_FREQ_QUESTION.getCommand(),
                        SystemMessage.USER_BOT_BENEFIT_MENU);
                break;
            case BACK_TO_OUR_PROJECT:
                messageSender.deleteMessage(user.getChatId(), callbackQuery.getMessage().getMessageId());
                ourProjectProcessor.run(user, Command.READ_OUR_PROJECT.getCommand(), SystemMessage.READ_OUR_PROJECT);
                break;
        }
    }

    @Autowired
    public void setOurProjectProcessor(OurProjectProcessor ourProjectProcessor) {
        this.ourProjectProcessor = ourProjectProcessor;
    }

    @Autowired
    public void setProjectExampleSender(ProjectExampleSender projectExampleSender) {
        this.projectExampleSender = projectExampleSender;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setBotBenefitProcessor(BotBenefitProcessor botBenefitProcessor) {
        this.botBenefitProcessor = botBenefitProcessor;
    }

    @Autowired
    public void setFreqQuestionAnswerSender(FreqQuestionAnswerSender freqQuestionAnswerSender) {
        this.freqQuestionAnswerSender = freqQuestionAnswerSender;
    }
}
