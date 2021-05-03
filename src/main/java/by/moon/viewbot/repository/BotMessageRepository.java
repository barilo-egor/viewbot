package by.moon.viewbot.repository;

import by.moon.viewbot.bean.BotMessage;
import by.moon.viewbot.enums.BotMessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotMessageRepository extends JpaRepository<BotMessage, Long> {
    Optional<BotMessage> findByType(BotMessageType type);
}
