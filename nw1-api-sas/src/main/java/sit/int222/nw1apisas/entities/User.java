package sit.int222.nw1apisas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "Users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    private String role;
    @Column(name = "createdOn", nullable = false, insertable = false, updatable = false)
    private ZonedDateTime createdOn;
    @Column(name = "updatedOn", nullable = false, insertable = false)
    private ZonedDateTime updatedOn;


}
