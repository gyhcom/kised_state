package state.member.infrastructure.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import state.member.application.command.member.MemberRegisterCommand;
import state.member.domain.entity.Member;

import java.util.Optional;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByUserId(String username);
    Boolean existsByEmail(String email);
    Boolean existsBySeqAndUserIdAndUsername(int id, String userId, String username);
}
