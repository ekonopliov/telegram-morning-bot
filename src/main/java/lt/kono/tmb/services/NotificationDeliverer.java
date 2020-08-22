package lt.kono.tmb.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import lt.kono.tmb.entities.Notification;
import lt.kono.tmb.providers.TelegramBotFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationDeliverer {

    public Notification deliver(Notification notification){

        SendResponse response = TelegramBotFactory.getInstance()
                .execute(new SendMessage(notification.getChatId(),notification.getMessage()));

        if(response.isOk()){
            notification.setDelivered(true);
            log.debug("Notification [" + notification.getId() + "] has been delivered!");
        }

        return notification;
    }
}
