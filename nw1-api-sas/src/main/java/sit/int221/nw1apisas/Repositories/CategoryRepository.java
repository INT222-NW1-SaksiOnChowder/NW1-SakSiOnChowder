package sit.int221.nw1apisas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.nw1apisas.Entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
