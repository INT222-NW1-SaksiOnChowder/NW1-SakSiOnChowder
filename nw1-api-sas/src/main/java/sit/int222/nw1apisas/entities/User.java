package sit.int222.nw1apisas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "Users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    @Column(name = "name", nullable = false, length = 180)
    private String name;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "role", nullable = false, length = 30)
    private String role;
    @Column(name = "createdOn", nullable = false)
    private ZonedDateTime createdOn;
    @Column(name = "updatedOn")
    private ZonedDateTime updatedOn;


//    public void setPassword(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        this.password = passwordEncoder.encode(password);
//    }
}
