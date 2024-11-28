package state.member.domain.repository;

import state.member.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findById(int seq);
    Optional<Member> findByUsername(String userId);
    void deleteById(int seq);
    Member getReferenceById(int seq);
    Boolean existsById(int seq);
}
