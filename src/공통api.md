// 📁 presentation/apis/StatIngestController.java
package state.member.presentation.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import state.member.application.processor.stat.StatIngestProcessor;
import state.member.presentation.request.CommonStatPayload;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatIngestController {

    private final StatIngestProcessor statIngestProcessor;

    @PostMapping("/ingest")
    public ResponseEntity<Void> ingest(@RequestBody CommonStatPayload payload) {
        statIngestProcessor.process(payload);
        return ResponseEntity.ok().build();
    }
}


// 📁 presentation/request/CommonStatPayload.java
package state.member.presentation.request;

import lombok.Data;
import java.util.List;

@Data
public class CommonStatPayload {
private String version;
private Meta meta;
private List<StatData> data;

    @Data
    public static class Meta {
        private String sourceSystem;
        private String dataType;
        private String date;
    }

    @Data
    public static class StatData {
        private String statType;
        private String count;
    }
}


// 📁 application/processor/stat/StatIngestProcessor.java
package state.member.application.processor.stat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import state.member.domain.entity.StatEntry;
import state.member.domain.service.StatIngestService;
import state.member.presentation.request.CommonStatPayload;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatIngestProcessor {

    private final StatIngestService statIngestService;

    public void process(CommonStatPayload payload) {
        List<StatEntry> entries = payload.getData().stream()
            .map(d -> StatEntry.builder()
                .sourceSystem(payload.getMeta().getSourceSystem())
                .dataType(payload.getMeta().getDataType())
                .date(LocalDate.parse(payload.getMeta().getDate()))
                .statType(d.getStatType())
                .count(d.getCount())
                .build())
            .collect(Collectors.toList());

        statIngestService.ingest(entries);
    }
}


// 📁 domain/entity/StatEntry.java
package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceSystem;

    @Column(nullable = false)
    private String dataType;

    @Column(nullable = false)
    private String statType;

    @Column(nullable = false)
    private String count;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();
}


// 📁 domain/repository/StatEntryRepository.java
package state.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import state.member.domain.entity.StatEntry;

public interface StatEntryRepository extends JpaRepository<StatEntry, Long> {
}


// 📁 domain/service/StatIngestService.java
package state.member.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import state.member.domain.entity.StatEntry;
import state.member.domain.repository.StatEntryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatIngestService {

    private final StatEntryRepository repository;

    public void ingest(List<StatEntry> entries) {
        repository.saveAll(entries);
    }
}