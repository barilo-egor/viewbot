package by.moon.viewbot.repository;

import by.moon.viewbot.bean.ProjectRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRequestRepository extends JpaRepository<ProjectRequest, Long> {
}
