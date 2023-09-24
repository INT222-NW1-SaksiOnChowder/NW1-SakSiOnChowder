package sit.int222.nw1apisas.dtos.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor

public class JwtRequest implements Serializable {

    private String username;
    private String password;

}
