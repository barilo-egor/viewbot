package by.moon.viewbot.bean;

import by.moon.viewbot.enums.CurrentStep;
import by.moon.viewbot.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{
    private Long chatId;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private CurrentStep currentStep;
    private String buffer;
}
