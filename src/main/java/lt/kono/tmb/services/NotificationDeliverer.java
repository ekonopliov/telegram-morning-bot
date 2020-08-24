package lt.kono.tmb.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import lt.kono.tmb.entities.Notification;
import lt.kono.tmb.providers.TelegramBotFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationDeliverer {

    public Notification deliver(Notification notification){

        TelegramBot bot = TelegramBotFactory.getInstance();
        SendResponse response =  bot.execute(new SendMessage(notification.getChatId(),notification.getMessage()));

        if(response.isOk()){
            notification.setDelivered(true);
            log.debug("Notification [" + notification.getId() + "] has been delivered!");
        }

        return notification;
    }
}
