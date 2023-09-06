package sit.int222.nw1apisas.dtos.users;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.validations.users.IsEmailFieldUnique;
import sit.int222.nw1apisas.validations.users.IsNameFieldUnique;
import sit.int222.nw1apisas.validations.users.IsUsernameFieldUnique;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @NotBlank(message = "must not be blank")
    @Size(max = 45, message = "size must be between 1 and 45")
    private String username;
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "size must be between 1 and 100")
    private String name;
    @NotBlank(message = "must not be blank")
    @Size(max = 100, message = "size must be between 1 and 150")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Email should be valid")
    private String email;
    private String role;

}
