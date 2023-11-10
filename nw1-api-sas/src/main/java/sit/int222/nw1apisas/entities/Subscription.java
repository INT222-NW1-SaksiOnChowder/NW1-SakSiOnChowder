package sit.int222.nw1apisas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Subscriptions")
@Setter
@Getter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "emailSubscription", nullable = false)
    private String emailSubscription;
    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName = "id", nullable = false)
    private User userId;
    @ManyToOne
    @JoinColumn(name="categoryId", referencedColumnName = "categoryId", nullable = false)
    private Category categoryId;

}
