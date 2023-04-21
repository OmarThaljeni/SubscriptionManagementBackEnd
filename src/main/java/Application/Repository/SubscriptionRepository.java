package Application.Repository;

import Application.Models.Subscription;
import Application.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    @Query(value = " SELECT * FROM Subscription s1 INNER JOIN users_subscriptions s2 ON s1.id = s2.subscription_id WHERE s2.user_id = ?1 ",nativeQuery=true)
    List<Subscription> findAllByUserId(Integer userId);

}
