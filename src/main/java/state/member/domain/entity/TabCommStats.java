package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Comment("시스템별 공통 통계 데이터")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TabCommStats {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("데이터 구조 버전")
    private String dVersion;

    @Column @Getter @Setter @Comment("데이터 전송 시스템 이름")
    private String sourceSystem;

    @Column @Getter @Setter @Comment("통계 데이터 구분 값")
    private String dataType;

    @Column @Getter @Setter @Comment("통계 데이터 기준일자")
    private LocalDate baseDt;

    @Column @Getter @Setter @Comment("통계 항목 구분")
    private String statType;

    @Column @Getter @Setter @Comment("통계 건수")
    private String cnt;

    @Column @Getter @Setter @Comment("등록 일자")
    private LocalDate regDt;
}
