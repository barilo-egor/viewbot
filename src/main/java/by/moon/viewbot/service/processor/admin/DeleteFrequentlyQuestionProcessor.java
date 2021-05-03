package by.moon.viewbot.service.processor.admin;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.beanservice.FrequentlyQuestionService;
import by.moon.viewbot.service.processor.BotBenefitProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class DeleteFrequentlyQuestionProcessor {
    private MessageSender messageSender;
    private BotBenefitProcessor botBenefitProcessor;
    private FrequentlyQuestionService frequentlyQuestionService;

    public void run(User user, CallbackQuery query){
        messageSender.deleteMessage(user.getChatId(), query.getMessage().getMessageId());
        frequentlyQuestionService.delete(frequentlyQuestionService
                .findById(Long.valueOf(query.getData().split(":")[1])));
        botBenefitProcessor.run(user, Command.DELETE_FREQ_QUESTION.getCommand(),
                SystemMessage.CHOOSE_FREQ_QUESTION_FOR_DELETE);
    }

    @Autowired
    public void setFrequentlyQuestionService(FrequentlyQuestionService frequentlyQuestionService) {
        this.frequentlyQuestionService = frequentlyQuestionService;
    }

    @Autowired
    public void setBotBenefitProcessor(BotBenefitProcessor botBenefitProcessor) {
        this.botBenefitProcessor = botBenefitProcessor;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
