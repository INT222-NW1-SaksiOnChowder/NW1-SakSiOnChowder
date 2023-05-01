package sit.int221.nw1apisas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int221.nw1apisas.Entities.Announcement;
import sit.int221.nw1apisas.Entities.Category;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
