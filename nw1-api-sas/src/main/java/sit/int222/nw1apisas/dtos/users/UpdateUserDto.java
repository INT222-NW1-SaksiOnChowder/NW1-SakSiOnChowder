package sit.int222.nw1apisas.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{1,}$", message = "Email should be valid")
    private String email;
    private String role;

}
