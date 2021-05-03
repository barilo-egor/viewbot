package by.moon.viewbot.service.processor.admin;

import by.moon.viewbot.bean.FrequentlyQuestion;
import by.moon.viewbot.bean.User;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.enums.CurrentStep;
import by.moon.viewbot.enums.SystemMessage;
import by.moon.viewbot.service.beanservice.FrequentlyQuestionService;
import by.moon.viewbot.service.beanservice.UserService;
import by.moon.viewbot.util.KeyboardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class AddNewFrequentlyQuestionProcessor {
    private UserService userService;
    private MessageSender messageSender;
    private FrequentlyQuestionService frequentlyQuestionService;

    public void saveQuestionTextToBuffer(User user, Message message){
        user.setBuffer(message.getText());
        user.setCurrentStep(CurrentStep.ADD_ANSWER_FOR_NEW_FREQ_QUESTION);
        userService.save(user);
        messageSender.sendMessage(user.getChatId(), SystemMessage.ENTER_NEW_ANSWER.getSystemMessage(),
                KeyboardFactory.getReplyOneRow(Command.CANCEL.getCommand()));
    }

    public void createFrequentlyQuestion(User user, Message message){
        FrequentlyQuestion frequentlyQuestion = new FrequentlyQuestion();
        frequentlyQuestion.setQuestion(user.getBuffer());
        frequentlyQuestion.setAnswer(message.getText());
        frequentlyQuestionService.save(frequentlyQuestion);
        user.setCurrentStep(CurrentStep.START);
        userService.save(user);
        messageSender.sendMessage(user.getChatId(), SystemMessage.SAVED.getSystemMessage());
    }

    @Autowired
    public void setFrequentlyQuestionService(FrequentlyQuestionService frequentlyQuestionService) {
        this.frequentlyQuestionService = frequentlyQuestionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
