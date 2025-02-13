package state.member.infrastructure;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

public class WebClientHandler {
    private static final Map<String, WebClient> WEB_CLIENT_CACHE = new HashMap<>();

    public static WebClient getWebClient(String baseUrl) {
        return WEB_CLIENT_CACHE.computeIfAbsent(baseUrl, url ->
                WebClient.builder()
                        .baseUrl(url)
                        .defaultHeader("Accept", "application/json")
                        .build()
        );
    }
}
