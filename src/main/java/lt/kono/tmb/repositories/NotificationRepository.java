package lt.kono.tmb.repositories;

import lt.kono.tmb.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * @param deliveryTime
     * @return List of Notification that were not delivered, and deliveryTime has passed
     */
    List<Notification> findByDeliveredFalseAndDeliveryTimeLessThan(Date deliveryTime);
}
