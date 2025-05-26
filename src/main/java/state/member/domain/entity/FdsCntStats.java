package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Comment("사업비점검 이상거래탐지")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FdsCntStats extends BaseEntity {
    /**
     * Nullable 여부는 추후에 추가 할 예정
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column  @Getter @Setter @Comment("총 이상거래 탐지 건수")
    private String detTotCnt;

    @Column @Getter @Setter @Comment("임시 날짜")
    private LocalDate baseDt2;
}
