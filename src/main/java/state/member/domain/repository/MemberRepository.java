package state.member.domain.repository;

import state.member.application.command.member.MemberRegisterCommand;
import state.member.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(int seq);
    Member getReferenceById(int seq);
    Boolean existsById(int seq);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
    Boolean existsBySeqAndUserIdAndUsername(int seq, String userId, String username);
    Optional<Member> findByUserId(String userId);
}
