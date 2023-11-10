package sit.int222.nw1apisas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.nw1apisas.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
