package lt.kono.tmb.providers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lt.kono.tmb.entities.User;
import lt.kono.tmb.news.Article;
import lt.kono.tmb.news.NewsService;
import lt.kono.tmb.repositories.NotificationRepository;
import lt.kono.tmb.repositories.UserRepository;
import lt.kono.tmb.weather.WeatherResponse;
import lt.kono.tmb.weather.WeatherService;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
@PropertySource("classpath:application.yml")
public class TelegramBotFactory {

    private static String telegramBotToken = "906959267:AAFiOKZxO05omG1nGOHPiy3DswVlnF7voKc";

    private static TelegramBot bot = new TelegramBot(telegramBotToken);

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NewsService newsService;

    @Autowired
    WeatherService weatherService;

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
                                    "Hey, bro " + firstName + "\n\n"
                                            + "All available commands:\n"
                                            + "/news - get latest news from BBC\n"
                                            + "/weather - get weather forecast for today\n"
                                            + "/morning - get morning routine report\n\n"
                                            + "Check out this project: https://github.com/ekonopliov/telegram-morning-bot/"));
                            break;
                        case "/news":
                            sendBBCNews(user);
                            //Adding subscription
//                            notificationRepository.save(Notification.builder()
//                                    .message("Latest morning news")
//                                    .chatId(user.getId())
//                                    .build());
                            break;
                        case "/weather":
                            try {
                                sendWeatherForecastToday(user, "Vilnius");
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            break;

                        case "/morning":
                            bot.execute(new SendMessage(user.getId(),
                                    "*Good morning, bro!*\n\n").parseMode(ParseMode.Markdown));
                            try {
                                sendBBCNews(user);
                                sendWeatherForecastToday(user, "Vilnius");
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }

                            bot.execute(new SendMessage(user.getId(),
                                    "\n\n*Have a nice day!*").parseMode(ParseMode.Markdown));
                            break;

                        //if command is unknown
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

    private void sendBBCNews(User user){
        StringBuilder news = new StringBuilder();

        for (Article a: newsService.getBBCNews().getArticles()) {
            news.append(a.toStringMarkdown() + " \n\n");
        }

        bot.execute(new SendMessage(user.getId(), "*On the news today:* \n\n" + news)
                .parseMode(ParseMode.Markdown));
    }

    private void sendWeatherForecastToday(User user, String cityName) throws URISyntaxException {
        StringBuilder weather = new StringBuilder();

        WeatherResponse weatherResponse = weatherService.getWeather(cityName, 1);

        int minTempC = (int) Math.round(weatherResponse
                .getForecast()
                .getForecastday()
                .get(0)
                .getDay().getMintempC());

        int maxTempC = (int) Math.round(weatherResponse
                .getForecast()
                .getForecastday()
                .get(0)
                .getDay().getMaxtempC());

        String conditions = weatherResponse
                .getForecast()
                .getForecastday()
                .get(0)
                .getDay()
                .getCondition()
                .getText();

        weather.append(conditions)
                .append(". ")
                .append("From: ")
                .append(minTempC)
                .append(" to ")
                .append(maxTempC)
                .append(" degrees.");
        bot.execute(new SendMessage(user.getId(), "*Weather today in " + cityName + ":*\n" + weather)
                .parseMode(ParseMode.Markdown));

        //Sending an icon of the weather conditions
        /*
        URI conditionsImageURL = new URIBuilder(weatherResponse
                .getForecast()
                .getForecastday()
                .get(0)
                .getDay()
                .getCondition()
                .getIcon())
                .setScheme("https")
                .build();
        bot.execute(new SendPhoto(user.getId(), conditionsImageURL.toString()));
         */
    }
}
