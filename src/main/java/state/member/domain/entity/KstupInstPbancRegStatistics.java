package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Comment("K-Startup 기관유형별 등록 건수(리스트)")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class KstupInstPbancRegStatistics extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("기관명")
    private String instNm;

    @Column @Getter @Setter @Comment("사업공고 등록 건수")
    private String bizPbancRegCnt;

    @Column @Getter @Setter @Comment("임시 날짜")
    private LocalDate baseDt2;
}
