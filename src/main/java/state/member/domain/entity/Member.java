package state.member.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.member.presentation.response.member.MemberResponseCommand;

@Builder
@Table(name = "MEMBER")
@ToString
@Setter
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    int seq;

    @Column(name = "USER_ID")
    String userId;

    @Column(name = "USERNAME")
    String username;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "USER_ROLE")
    String userRole;

    @Column(name = "EMAIL")
    String email;

    @Column(name = "DEPT_CD")
    String departmentCode;

    @Column(name = "PSIT_CD")
    String positionCode;

    public Member() {}

    public Member(
            int seq,
            String userId,
            String username,
            String password,
            String userRole,
            String email,
            String departmentCode,
            String positionCode) {
        this.seq = seq;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.email = email;
        this.departmentCode = departmentCode;
        this.positionCode = positionCode;
    }

    public MemberResponseCommand toCommand() {
        return MemberResponseCommand.builder()
                .seq(this.seq)
                .userId(this.userId)
                .username(this.username)
                .userRole(this.userRole)
                .email(this.email)
                .departmentCode(this.departmentCode)
                .positionCode(this.positionCode)
                .build();
    }
}