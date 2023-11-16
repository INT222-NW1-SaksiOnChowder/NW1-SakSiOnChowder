package sit.int222.nw1apisas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findByCategoryIdAndSubscriberEmail(Category categoryId, String email);
    List<Subscription> findAllBySubscriberEmail(String email);
}
