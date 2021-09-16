package recipes.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @JsonIgnore
    private long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Convert(converter = ArrayListConverter.class)
    @NotEmpty(message = "Ingredients must have at least one entry")
    private List<String> ingredients;

    @Convert(converter = ArrayListConverter.class)
    @NotEmpty(message = "Directions must have at least one entry")
    private List<String> directions;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    private LocalDateTime date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String creator;

    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        date = LocalDateTime.now();
    }
}
