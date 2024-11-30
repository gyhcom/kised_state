package state.admin.userManage.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.admin.userManage.application.command.UserResponseCommand;
import state.admin.userManage.domain.auth.AuthRole;
import state.member.presentation.response.MemberResponseCommand;

@Getter
@Setter
@Entity
@Table(name = "user")
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private int seq;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NM")
    private String userNm;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private AuthRole userRole;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DEPT_CD")
    private String deptCd;

    @Column(name = "MANAGE_CD")
    private String manageCd;

    public User() {
    }

    public User(int seq, String userId, String userNm, String password, AuthRole userRole, String email, String deptCd, String manageCd) {
        this.seq = seq;
        this.userId = userId;
        this.userNm = userNm;
        this.password = password;
        this.userRole = userRole;
        this.email = email;
        this.deptCd = deptCd;
        this.manageCd = manageCd;
    }

    public UserResponseCommand toCommand() {
        return UserResponseCommand.builder()
                .userId(this.userId)
                .userNm(this.userNm)
                .userRole(this.userRole)
                .email(this.email)
                .deptCd(this.deptCd)
                .manageCd(this.manageCd)
                .build();
    }
}
