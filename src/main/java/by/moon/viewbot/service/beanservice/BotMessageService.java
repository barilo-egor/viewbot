package by.moon.viewbot.service.beanservice;

import by.moon.viewbot.bean.BotMessage;
import by.moon.viewbot.bot.MessageSender;
import by.moon.viewbot.enums.BotMessageType;
import by.moon.viewbot.repository.BotMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotMessageService {
    private BotMessageRepository botMessageRepository;
    private MessageSender messageSender;

    public BotMessage save(BotMessage botMessage){
        return botMessageRepository.save(botMessage);
    }

    public BotMessage findByType(BotMessageType botMessageType){
        return botMessageRepository.findByType(botMessageType)
                .orElseGet(() -> botMessageRepository.save(BotMessage.builder()
                        .type(botMessageType)
                        .text("Значение \"" + botMessageType + "\" не установлено.")
                        .build()
                ));
    }

    public void send(BotMessageType botMessageType, long chatId){
        BotMessage botMessage = findByType(botMessageType);
        if(botMessage.getPhoto() != null) messageSender.sendPhoto(chatId, botMessage.getText(), botMessage.getPhoto());
        else messageSender.sendMessage(chatId, botMessage.getText());
    }

    @Autowired
    public void setBotMessageRepository(BotMessageRepository botMessageRepository) {
        this.botMessageRepository = botMessageRepository;
    }

    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
