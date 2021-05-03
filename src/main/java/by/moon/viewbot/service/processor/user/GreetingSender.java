package by.moon.viewbot.service.processor.user;

import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.BotMessageType;
import by.moon.viewbot.service.beanservice.BotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingSender {
    private BotMessageService botMessageService;
    private MessageSender messageSender;

    public void send(long chatId){
        messageSender.sendMessage(chatId, botMessageService.findByType(BotMessageType.GREETING).getText());
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Autowired
    public void setBotMessageService(BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
    }
}
