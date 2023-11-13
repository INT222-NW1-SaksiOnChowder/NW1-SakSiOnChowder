package sit.int222.nw1apisas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findByCategoryIdAndEmailSubscription(Category categoryId, String email);

    Subscription findByEmailSubscription(String email);

    Subscription findByEmailSubscriptionAndOtp(String email, String otp);


    List<Subscription> findAllByEmailSubscription(String email);
}
