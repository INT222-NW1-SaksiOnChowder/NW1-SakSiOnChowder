package sit.int221.nw1apisas.Entities;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Categories")
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    private Integer categoryId;

    @Column(name = "categoryName", nullable = false, length = 50)
    private String categoryName;
}
