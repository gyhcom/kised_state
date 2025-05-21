package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.exception.ErrorCode;

import java.util.Map;

@Comment("창업기업확인")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CertCountStatistics extends BaseEntity {
    /**
     * Nullable 여부는 추후에 추가 할 예정
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Comment("등록 순번")
    private int seq;

    @Column @Getter @Setter @Comment("확인서 신청 건수")
    private String confmDocAplyCnt;

    @Column @Getter @Setter @Comment("확인서 발급 건수")
    private String confmDocIssuCnt;

    @Column @Getter @Setter @Comment("확인서 반려 건수")
    private String confmDocRjctCnt;

    @Column @Getter @Setter @Comment("방문자 수")
    private String vstCnt;
}
