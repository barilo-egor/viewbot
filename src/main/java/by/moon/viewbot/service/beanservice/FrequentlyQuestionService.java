package by.moon.viewbot.service.beanservice;

import by.moon.viewbot.bean.FrequentlyQuestion;
import by.moon.viewbot.exceptions.FrequentlyQuestionNotFoundException;
import by.moon.viewbot.repository.FrequentlyQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrequentlyQuestionService {
    private FrequentlyQuestionRepository frequentlyQuestionRepository;

    public void delete(FrequentlyQuestion frequentlyQuestion){
        frequentlyQuestionRepository.delete(frequentlyQuestion);
    }

    public FrequentlyQuestion findById(Long id){
        return frequentlyQuestionRepository.findById(id).orElseThrow(FrequentlyQuestionNotFoundException::new);
    }

    public FrequentlyQuestion save(FrequentlyQuestion frequentlyQuestion){
        return frequentlyQuestionRepository.save(frequentlyQuestion);
    }

    public List<FrequentlyQuestion> findAll(){
        return frequentlyQuestionRepository.findAll();
    }

    @Autowired
    public void setFrequentlyQuestionRepository(FrequentlyQuestionRepository frequentlyQuestionRepository) {
        this.frequentlyQuestionRepository = frequentlyQuestionRepository;
    }
}
