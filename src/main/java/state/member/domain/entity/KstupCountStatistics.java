package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Comment("K-Startup 단 건 데이터")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KstupCountStatistics extends BaseEntity {
    /**
     * Nullable 여부는 추후에 추가 할 예정
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("로그인 수")
    private String lginCnt;

    @Column @Getter @Setter @Comment("로그인 수(중복제거)")
    private String lginDupCnt;

    @Column @Getter @Setter @Comment("회원 수")
    private String mnpwCnt;

    @Column @Getter @Setter @Comment("방문자 수")
    private String vstCnt;

    @Column @Getter @Setter @Comment("통합공고 등록 건수")
    private String intgPbancRegCnt;

    @Column @Getter @Setter @Comment("사업공고 등록 건수")
    private String bizPbancRegInstCnt;

    @Column @Getter @Setter @Comment("임시 날짜")
    private LocalDate baseDt2;
}
