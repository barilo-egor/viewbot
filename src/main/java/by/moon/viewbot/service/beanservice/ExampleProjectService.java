package by.moon.viewbot.service.beanservice;

import by.moon.viewbot.bean.ExampleProject;
import by.moon.viewbot.exceptions.ExampleProjectNotFoundException;
import by.moon.viewbot.repository.ExampleProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleProjectService {
    private ExampleProjectRepository exampleProjectRepository;

    public ExampleProject save(ExampleProject exampleProject){
        return exampleProjectRepository.save(exampleProject);
    }

    public List<ExampleProject> findAll(){
        return exampleProjectRepository.findAll();
    }

    public ExampleProject findById(long id){
        return exampleProjectRepository.findById(id).orElseThrow(ExampleProjectNotFoundException::new);
    }

    public void delete(ExampleProject exampleProject){
        exampleProjectRepository.delete(exampleProject);
    }

    @Autowired
    public void setExampleProjectRepository(ExampleProjectRepository exampleProjectRepository) {
        this.exampleProjectRepository = exampleProjectRepository;
    }
}
