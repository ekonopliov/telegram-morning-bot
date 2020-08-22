package lt.kono.tmb.providers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lt.kono.tmb.entities.Notification;
import lt.kono.tmb.entities.User;
import lt.kono.tmb.repositories.NotificationRepository;
import lt.kono.tmb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class TelegramBotFactory {
    private static TelegramBot bot = new TelegramBot("TOKEN_HERE");

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void init(){
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {

                updates.forEach(update -> {
                    Long chatId = update.message().chat().id();
                    String messageText = update.message().text();
                    String firstName = update.message().chat().firstName();
                    String lastName = update.message().chat().lastName();

                    User user = userRepository.findById(chatId).orElseGet(() -> userRepository.save(User.builder()
                            .id(chatId)
                            .firstName(firstName)
                            .lastName(lastName)
                            .build()));

                    switch (messageText){
                        case "/start":
                            bot.execute(new SendMessage(user.getId(),
                                    "Hey, bro " + firstName));
                            break;
                        case "/news":
                            bot.execute(new SendMessage(user.getId(), "Here are your news:"));
                            notificationRepository.save(Notification.builder()
                                    .message("Latest morning news")
                                    .chatId(user.getId())
                                    .build());
                            break;
                        default:
                            bot.execute(new SendMessage(user.getId(),
                                    "Oh, that's something new. I do not know this command yet"));
                    }
                });

                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    public static TelegramBot getInstance() {
        return bot;
    }

    private TelegramBotFactory() {
    }
}
