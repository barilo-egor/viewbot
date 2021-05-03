package by.moon.viewbot.bean;

import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExampleProject extends BaseEntity{
    private String name;
    private String link;
    private String description;
}
