package by.moon.viewbot.service.beanservice;

import by.moon.viewbot.bean.ProjectRequest;
import by.moon.viewbot.exceptions.ProjectRepositoryNotFoundException;
import by.moon.viewbot.repository.ProjectRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRequestService {
    private ProjectRequestRepository projectRequestRepository;

    public List<ProjectRequest> findAll(){
        return projectRequestRepository.findAll();
    }

    public ProjectRequest findById(long id){
        return projectRequestRepository.findById(id).orElseThrow(ProjectRepositoryNotFoundException::new);
    }

    public ProjectRequest save(ProjectRequest projectRequest){
        return projectRequestRepository.save(projectRequest);
    }

    public void delete(ProjectRequest projectRequest){
        projectRequestRepository.delete(projectRequest);
    }

    @Autowired
    public void setProjectRequestRepository(ProjectRequestRepository projectRequestRepository) {
        this.projectRequestRepository = projectRequestRepository;
    }
}
