package by.moon.viewbot.repository;

import by.moon.viewbot.bean.ExampleProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleProjectRepository extends JpaRepository<ExampleProject, Long> {
}
