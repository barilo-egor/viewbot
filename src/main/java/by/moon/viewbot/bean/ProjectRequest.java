package by.moon.viewbot.bean;

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
public class ProjectRequest extends BaseEntity{
    private Boolean isActive;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String description;
}
