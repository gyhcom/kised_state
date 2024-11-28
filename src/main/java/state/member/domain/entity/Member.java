package state.member.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.member.presentation.response.MemberResponseCommand;

@Builder
@Table(name = "USER")
@ToString
@Setter
@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "SEQ", nullable = false)
    int seq;

    @Column(name = "USER_ID")
    String username;

    @Column(name = "PASSWORD")
    String password;

    @Column(name = "USER_ROLE")
    String userRole;

    public Member() {}

    public Member(int seq, String username, String password, String userRole) {
        this.seq = seq;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public MemberResponseCommand toCommand() {
        return MemberResponseCommand.builder()
                .seq(this.seq)
                .userId(this.username)
                .userRole(this.userRole)
                .build();
    }
}