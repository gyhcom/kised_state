package state.member.infrastructure;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientHandler {
    public static WebClient getWebClient(String baseUrl) {
        return WebClient.builder()
                    .baseUrl(baseUrl) // 기본 URL 설정
                    .defaultHeader("Accept", "application/json") // 기본 헤더 설정
                    .build();

    }
}
