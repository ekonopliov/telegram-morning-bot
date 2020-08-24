# Telegram Morning Routine Bot
The bot can send you latest **news from BBC or give you a weather forecast** for the great upcoming day. Developed with Java 11, Spring Boot and [Telegram Bot API](https://github.com/pengrad/java-telegram-bot-api).  Fetching information from  [WeatherAPI](https://www.weatherapi.com) and [NewsAPI](https://newsapi.org).  
  
![](telegram-bot.gif)  

# Build and run yourself
**Note:** fill all \<TOKEN-HERE\> placeholders with tokens from @BotFather, [WeatherAPI](https://www.weatherapi.com) and [NewsAPI](https://newsapi.org).  

Preconditions: Docker up and running, Maven 3.6.3+, OpenJDK11 (and all PATHs)  

At root directory:  
`docker-compose up -d `  
`mvn clean install`  
`mvn spring-boot:run` (or run TelegramMorningBot.java as java class)  