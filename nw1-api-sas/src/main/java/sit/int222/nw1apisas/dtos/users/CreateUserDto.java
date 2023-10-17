package sit.int222.nw1apisas.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "must not be blank")
    @Size(max = 45, min = 1, message = "size must be between 1 and 45")
    @IsUsernameFieldUnique
    private String username;
    @NotBlank(message = "must not be blank")
    @Pattern(regexp = "^(?!.*\\s)(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$", message = "must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters")
    private String password;
    @NotBlank(message = "must not be blank")
    @Size(max = 100, min = 1, message = "size must be between 1 and 100")
    @IsNameFieldUnique
    private String name;
    @NotBlank(message = "must not be blank")
    @Size(max = 150, min = 1, message = "size must be between 1 and 150")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{1,}$", message = "Email should be valid")
    @IsEmailFieldUnique
    private String email;
    private String role;

}



