package sit.int221.nw1apisas.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Categories")
@Setter
@Getter
public class Category {
    @Id
    @Column(name = "categoryId", nullable = false)
    private Integer categoryId1;

    @Column(name = "categoryName", nullable = false, length = 50)
    private String categoryName;
}
