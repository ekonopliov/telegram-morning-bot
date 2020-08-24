package lt.kono.tmb.entities;

import lt.kono.tmb.utils.DateProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    public void shouldBuildDefault(){
        String message = "test";

        Notification notification = Notification.builder()
                .message(message)
                .build();

        Assertions.assertEquals(message, notification.getMessage());
        Assertions.assertFalse(notification.getDelivered());
    }

    @Test
    public void shouldBuildCustom(){
        String message = "test";
        Boolean delivered = true;
        Date date = DateProvider.addHours(new Date(), 4);
        Long chatId = 9999L;

        Notification notification = Notification.builder()
                .chatId(chatId)
                .deliveryTime(date)
                .creationTime(date)
                .delivered(delivered)
                .message(message)
                .build();

        Assertions.assertEquals(message, notification.getMessage());
        Assertions.assertEquals(delivered, notification.getDelivered());
        Assertions.assertEquals(date, notification.getCreationTime());
        Assertions.assertEquals(date, notification.getDeliveryTime());
        Assertions.assertEquals(chatId, notification.getChatId());
    }
}