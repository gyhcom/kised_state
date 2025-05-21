package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("사업비점검 유형별 탐지건수")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FdsDetCntByTypeStats extends BaseEntity {
    /**
     * Nullable 여부는 추후에 추가 할 예정
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("탐지유형코드")
    private String detTpCd;

    @Column @Getter @Setter @Comment("탐지유형명")
    private String detTpNm;

    @Column @Getter @Setter @Comment("탐지구분명")
    private String detSeNm;

    @Column @Getter @Setter @Comment("탐지 건수")
    private String cnt;
}
