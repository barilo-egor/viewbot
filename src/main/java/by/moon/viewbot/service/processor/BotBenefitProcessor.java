package by.moon.viewbot.service.processor;

import by.moon.viewbot.bean.FrequentlyQuestion;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.InlineType;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.beanservice.FrequentlyQuestionService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotBenefitProcessor {
    private FrequentlyQuestionService frequentlyQuestionService;
    private MessageSender messageSender;

    public void run(User user, String inputData, SystemMessage systemMessage){
        List<FrequentlyQuestion> frequentlyQuestionList = frequentlyQuestionService.findAll();
        if(frequentlyQuestionList.size() == 0){
            messageSender.sendMessage(user.getChatId(), SystemMessage.EMPTY.getSystemMessage());
            return;
        }
        String[] questions = new String[frequentlyQuestionList.size()];
        String[] data = new String[frequentlyQuestionList.size()];
        int i = 0;
        for(FrequentlyQuestion frequentlyQuestion : frequentlyQuestionList){
            questions[i] = frequentlyQuestion.getQuestion();
            data[i] = inputData + ":" + frequentlyQuestion.getId();
            i++;
        }
        messageSender.sendMessage(user.getChatId(), systemMessage.getSystemMessage(),
                KeyboardFactory.getInlineOneRow(questions, data, InlineType.DATA));
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
