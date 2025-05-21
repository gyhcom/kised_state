package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Comment("기관 홈페이지")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KisedorkrCountStatistics extends BaseEntity {
    /**
     * Nullable 여부는 추후에 추가 할 예정
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("방문자 수")
    private String vstCnt;
}
