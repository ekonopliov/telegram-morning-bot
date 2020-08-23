package lt.kono.tmb.news;

import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    private RestTemplate restTemplate = new RestTemplate();

    private static String headlinesURL = "https://newsapi.org/v2/top-headlines";
    private static String newsApiToken = "TOKEN-HERE";

    @SneakyThrows
    public NewsResponse getBBCNews() {

        URIBuilder uriBuilder = new URIBuilder(headlinesURL)
                .addParameter("sources", "bbc-news")
                .addParameter("apiKey", newsApiToken);

        return restTemplate.getForObject(uriBuilder.build(), NewsResponse.class);
    }
}