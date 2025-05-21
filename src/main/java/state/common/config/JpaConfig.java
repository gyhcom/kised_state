package state.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * BaseEntity에서 등록일자를 자동으로 입력되도록 하기 위함
 */
@EnableJpaAuditing
@Configuration
public class JpaConfig {
}
