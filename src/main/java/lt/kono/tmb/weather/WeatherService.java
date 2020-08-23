package lt.kono.tmb.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private RestTemplate restTemplate = new RestTemplate();

    private static String weatherForecastURL = "http://api.weatherapi.com/v1/forecast.json";
    private static String weatherApiToken = "TOKEN-HERE";

    @SneakyThrows
    public WeatherResponse getWeather(String cityName, int forecastDays) {

        URIBuilder uriBuilder = new URIBuilder(weatherForecastURL)
                .addParameter("q", cityName)
                .addParameter("days", String.valueOf(forecastDays))
                .addParameter("key", weatherApiToken);


        String jsonResponse = restTemplate.getForObject(uriBuilder.build(), String.class);

        return new ObjectMapper().readValue(jsonResponse, WeatherResponse.class);
    }
}
