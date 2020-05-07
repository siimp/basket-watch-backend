package basket.watch.backend.scraper;

import io.micronaut.context.annotation.Factory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.inject.Singleton;

@Factory
public class ApacheHttpClientFactory {

    @Singleton
    CloseableHttpClient apacheHttpClient() {
        RequestConfig config = RequestConfig.copy(RequestConfig.DEFAULT)
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .build();
    }
}
