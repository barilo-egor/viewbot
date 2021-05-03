package by.moon.viewbot.enums;

import by.moon.viewbot.exceptions.UpdateTypeNotFoundException;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Update;

public enum UpdateType {
    NONE,
    MESSAGE,
    CALLBACK_QUERY;

    @SneakyThrows
    public static UpdateType getUpdateType(Update update){
        if (update.hasCallbackQuery()) return CALLBACK_QUERY;
        else if(update.hasMessage()) return MESSAGE;
        throw new UpdateTypeNotFoundException();
    }
}
