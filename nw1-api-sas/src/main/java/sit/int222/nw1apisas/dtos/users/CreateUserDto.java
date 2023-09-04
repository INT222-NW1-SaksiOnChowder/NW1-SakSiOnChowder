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
public class CreateUserDto {
    @NotNull(message = "username must not be null")
    @NotEmpty(message = "username must not be empty")
    @Size(max = 45, message = "username out of range")
    @IsUsernameFieldUnique
    private String username;
    @NotNull(message = "password must not be null")
    @NotEmpty(message = "username must not be empty")
    @Pattern(regexp = "^(?!.*\\s)(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$", message = "Password must have at least")
    private String Password;
    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    @Size(max = 100, message = "name out of range")
    @IsNameFieldUnique
    private String name;
    @NotNull(message = "email must not be null")
    @NotEmpty(message = "email must not be empty")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "user's email is invalid.")
    @IsEmailFieldUnique
    private String email;
    private String role;

}



