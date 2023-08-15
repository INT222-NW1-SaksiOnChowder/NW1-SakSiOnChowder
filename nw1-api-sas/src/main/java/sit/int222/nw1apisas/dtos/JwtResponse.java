package sit.int222.nw1apisas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private String jwttoken;

//    public JwtResponse(String jwttoken) {
//        this.jwttoken = jwttoken;
//    }
//
//    public String getToken() {
//        return this.jwttoken;
//    }
}
