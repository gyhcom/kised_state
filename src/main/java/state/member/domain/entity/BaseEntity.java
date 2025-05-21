package state.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 모든 테이블에 공통적으로 존재하는 필드를 관리함
 * 엔티티 클래스가 아님
 * 자식 엔티티에게 매핑 정보만 전달
 */
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    @Comment("등록일자")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    @Column(nullable = false)
    protected LocalDate regDt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    @Column(nullable = false)
    @Comment("데이터 기준 일자 -> API Body에 담겨 있어야 하기 때문에 추후 수정될 예정")
    private LocalDate baseDt;
}
