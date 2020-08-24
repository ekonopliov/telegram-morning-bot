package lt.kono.tmb.repositories;

import lt.kono.tmb.entities.Notification;
import lt.kono.tmb.utils.DateProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    Notification notification;
    String message = "test";
    Boolean delivered = true;
    Date date = DateProvider.addHours(new Date(), 4);
    Long chatId = 9999L;

    @BeforeEach
    void setup(){

        notification = Notification.builder()
                .chatId(chatId)
                .deliveryTime(date)
                .creationTime(date)
                .delivered(delivered)
                .message(message)
                .build();
    }

    @Test
    void shouldSaveAndRetrieve(){

        notificationRepository.save(notification);

        Notification retrievedNotification = notificationRepository.getOne(notification.getId());

        Assertions.assertEquals(message, retrievedNotification.getMessage());
        Assertions.assertEquals(delivered, retrievedNotification.getDelivered());
        Assertions.assertEquals(date, retrievedNotification.getCreationTime());
        Assertions.assertEquals(date, retrievedNotification.getDeliveryTime());
        Assertions.assertEquals(chatId, retrievedNotification.getChatId());
    }

    @Test
    void shouldFindUndelivered(){

        Notification earlyNotification = Notification.builder()
                .chatId(chatId)
                .creationTime(date)
                .delivered(delivered)
                .message("Other message")
                .build();

        notification.setDelivered(false);
        notification.setDeliveryTime(DateProvider.substractSeconds(new Date(), 100));

        earlyNotification.setDelivered(false);
        earlyNotification.setDeliveryTime(DateProvider.addSeconds(new Date(), 100));

        notificationRepository.save(notification);
        notificationRepository.save(earlyNotification);

        List<Notification> retrievedNotifications = notificationRepository.findByDeliveredFalseAndDeliveryTimeLessThan(new Date());

        Assertions.assertFalse(retrievedNotifications.isEmpty());
        Assertions.assertEquals(1, retrievedNotifications.size());
        Assertions.assertEquals(notification.getMessage(), retrievedNotifications.get(0).getMessage());


    }


}