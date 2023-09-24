package sit.int222.nw1apisas.dtos.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor

public class JwtResponse implements Serializable {

    private final String jwtToken;

}