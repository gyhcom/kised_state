package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Comment("법인설립시스템")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class StartbizCountStatistics extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("법인 설립 건수")
    private String corpFndnCnt;

    @Column @Getter @Setter @Comment("방문자 수")
    private String vstCnt;

    @Column @Getter @Setter @Comment("임시 날짜")
    private LocalDate baseDt2;
}
