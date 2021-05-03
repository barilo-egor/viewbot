package by.moon.viewbot.service.processor.user;

import by.moon.viewbot.bean.FrequentlyQuestion;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.InlineType;
import by.moon.viewbot.service.beanservice.FrequentlyQuestionService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class FreqQuestionAnswerSender {
    private MessageSender messageSender;
    private FrequentlyQuestionService frequentlyQuestionService;

    public void send(long chatId, CallbackQuery query){
        FrequentlyQuestion frequentlyQuestion =
                frequentlyQuestionService.findById(Long.parseLong(query.getData().split(":")[1]));
        messageSender.sendEditedMessage(chatId, query.getMessage().getMessageId(),
                frequentlyQuestion.getAnswer(), KeyboardFactory.getInlineOneRow(
                        new String[]{"Вернуться к вопросам"},
                        new String[]{Command.BACK_TO_BOT_BENEFIT.getCommand()},
                        InlineType.DATA
                ));
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setFrequentlyQuestionService(FrequentlyQuestionService frequentlyQuestionService) {
        this.frequentlyQuestionService = frequentlyQuestionService;
    }
}
