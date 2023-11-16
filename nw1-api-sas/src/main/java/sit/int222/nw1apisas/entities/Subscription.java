package sit.int222.nw1apisas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "Subscriptions")
@Setter
@Getter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "subscriberEmail", nullable = false)
    private String subscriberEmail;
    @Column(name = "createdOn", nullable = false, insertable = false, updatable = false)
    private ZonedDateTime createdOn;
    @Column(name = "updatedOn", nullable = false, insertable = false, updatable = false)
    private ZonedDateTime updatedOn;
    @ManyToOne
    @JoinColumn(name="categoryId", referencedColumnName = "categoryId", nullable = false)
    private Category categoryId;

}
