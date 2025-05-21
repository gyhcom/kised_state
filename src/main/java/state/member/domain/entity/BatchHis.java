package state.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable /* 테이블 변경이나 insert, update 시도하지 않음*/
@Table(name = "BATCH_STEP_EXECUTION")
@Entity
public class BatchHis {
    @Id
    private Long stepExecutionId;

    @Column @Getter @Setter
    private String stepName;

    @Column @Getter @Setter @DateTimeFormat(pattern = "YYYY-MM-DD HH24:MI:SS")
    private LocalDateTime startTime;

    @Column @Getter @Setter @DateTimeFormat(pattern = "YYYY-MM-DD HH24:MI:SS")
    private LocalDateTime endTime;

    @Column @Getter @Setter
    private String status;

    @Column @Getter @Setter
    private String exitMessage;
}
