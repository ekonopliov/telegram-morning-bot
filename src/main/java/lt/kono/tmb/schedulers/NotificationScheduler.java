package lt.kono.tmb.schedulers;

import lombok.extern.slf4j.Slf4j;
import lt.kono.tmb.repositories.NotificationRepository;
import lt.kono.tmb.services.NotificationDeliverer;
import lt.kono.tmb.utils.DateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationScheduler {

    @Autowired
    NotificationRepository repository;

    @Autowired
    NotificationDeliverer deliverer;

    @Scheduled(fixedDelay = 30000)
    public void findUndeliveredNotifications(){
        log.debug("Running scheduled search for undelivered notifications");

        repository.findByDeliveredFalseAndDeliveryTimeLessThan(DateProvider.getCurrentDate())
                .forEach(notification -> repository.save(deliverer.deliver(notification)));
    }
}
