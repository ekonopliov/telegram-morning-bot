package lt.kono.tmb.repositories;

import lt.kono.tmb.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByDeliveredFalse();

    List<Notification> findByDeliveredFalseAndDeliveryTimeLessThan(Date deliveryTime);
}
