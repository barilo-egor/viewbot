package by.moon.viewbot.service.dispatchers;

import by.moon.viewbot.bean.User;
import by.moon.viewbot.enums.Command;
import by.moon.viewbot.service.processor.admin.DeleteFrequentlyQuestionProcessor;
import by.moon.viewbot.service.processor.admin.DeleteOurProjectProcessor;
import by.moon.viewbot.service.processor.admin.NewRequestsProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class AdminCallbackQueryDispatcher {
    private DeleteFrequentlyQuestionProcessor deleteFrequentlyQuestionProcessor;
    private DeleteOurProjectProcessor deleteOurProjectProcessor;
    private NewRequestsProcessor newRequestsProcessor;

    public void dispatch(CallbackQuery callbackQuery, User user){
        switch (Command.fromString(callbackQuery.getData())){
            default:
                if(callbackQuery.getData().startsWith(Command.DELETE_FREQ_QUESTION.getCommand())){
                    deleteFrequentlyQuestionProcessor.run(user, callbackQuery);
                } else if(callbackQuery.getData().startsWith(Command.DELETE_OUR_PROJECT.getCommand())){
                    deleteOurProjectProcessor.run(user, callbackQuery);
                } else if(callbackQuery.getData().startsWith(Command.HIDE_REQUEST.getCommand())){
                    newRequestsProcessor.hideRequest(user, callbackQuery);
                }
                break;
        }
    }

    @Autowired
    public void setNewRequestsProcessor(NewRequestsProcessor newRequestsProcessor) {
        this.newRequestsProcessor = newRequestsProcessor;
    }

    @Autowired
    public void setDeleteOurProjectProcessor(DeleteOurProjectProcessor deleteOurProjectProcessor) {
        this.deleteOurProjectProcessor = deleteOurProjectProcessor;
    }

    @Autowired
    public void setDeleteFrequentlyQuestionProcessor(DeleteFrequentlyQuestionProcessor deleteFrequentlyQuestionProcessor) {
        this.deleteFrequentlyQuestionProcessor = deleteFrequentlyQuestionProcessor;
    }
}
