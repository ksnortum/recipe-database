package recipes.businesslayer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {
    @Id
    @Pattern(regexp = "^[^@]+@[^.]+\\..+$", message = "Invalid email")
    private String email;

    @Size(min = 8, message = "Password must be at least eight characters in length")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
