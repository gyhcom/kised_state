package state.common.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@ConfigurationProperties(prefix = "services.urls")
public class ExternalUrlsConfig {
    private final String kstup;
    private final String kisedorkr;
    private final String cert;
    private final String startbiz;
    private final String fds;
    private final String gslsPms;
}
