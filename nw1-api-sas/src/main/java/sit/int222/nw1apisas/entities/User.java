package sit.int222.nw1apisas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sit.int222.nw1apisas.enums.Role;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "createdOn", nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdOn;
    @Column(name = "updatedOn", nullable = false)
    @UpdateTimestamp
    private ZonedDateTime updatedOn;


}
