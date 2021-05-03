package by.moon.viewbot.bean;

import lombok.*;
import by.moon.viewbot.enums.BotMessageType;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BotMessage extends BaseEntity{
    @Column(unique = true)
    private BotMessageType type;
    private String text;
    private String photo;
}
