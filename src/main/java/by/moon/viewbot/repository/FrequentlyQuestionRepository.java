package by.moon.viewbot.repository;

import by.moon.viewbot.bean.FrequentlyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequentlyQuestionRepository extends JpaRepository<FrequentlyQuestion, Long> {
}
