package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("국고보조금(PMS)")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GslsPmsCountStatistics extends BaseEntity {
    /**
     * Nullable 여부는 추후에 추가 할 예정
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("수급자집행정보 건수")
    private String excutCnt;

    @Column @Getter @Setter @Comment("정보공시내역 건수")
    private String ifpbntCnt;

    @Column @Getter @Setter @Comment("세입세출내역 건수")
    private String anlrveCnt;

    @Column @Getter @Setter @Comment("재무제표결산 내역 건수")
    private String fnlttCnt;

    @Column @Getter @Setter @Comment("상세내역사업정보 건수")
    private String ddtlbzCnt;
}
