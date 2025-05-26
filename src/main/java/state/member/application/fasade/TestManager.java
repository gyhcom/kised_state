package state.member.application.fasade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import state.member.application.processor.TestApiProcessor;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestManager {
    private final TestApiProcessor testApiProcessor;

    public Mono<Map<String, Object>> testApi(LocalDate date) {
        return testApiProcessor.execute(date);
    }
}
