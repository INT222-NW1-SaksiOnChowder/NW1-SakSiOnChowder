package sit.int222.nw1apisas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int222.nw1apisas.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateUserDto {
    private String username;
    private String name;
    private String email;
    private Role role;
}
