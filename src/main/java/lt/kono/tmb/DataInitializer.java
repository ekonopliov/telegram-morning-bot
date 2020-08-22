package lt.kono.tmb;

import lombok.extern.slf4j.Slf4j;
import lt.kono.tmb.entities.Notification;
import lt.kono.tmb.repositories.NotificationRepository;
import lt.kono.tmb.utils.DateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void run(String... args) throws Exception {
//        notificationRepository.save(Notification.builder()
//                .message("Hello, this is first your notification")
//                .build());
//
//        notificationRepository.save(Notification.builder()
//                .message("Hello, this is your second notification :)")
//                .deliveryTime(DateProvider.addSeconds(DateProvider.getCurrentDate(), 10))
//                .build());
    }
}
