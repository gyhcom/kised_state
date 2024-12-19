package state.member.infrastructure;

import org.springframework.stereotype.Repository;
import state.member.application.command.member.MemberRegisterCommand;
import state.member.domain.entity.Member;
import state.member.domain.repository.MemberRepository;
import state.member.infrastructure.JPA.JpaMemberRepository;

import java.util.Optional;

@Repository
public class MemberRepositoryAdapter implements MemberRepository {
    private final JpaMemberRepository jpaMemberRepository;

    public MemberRepositoryAdapter(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public void save(Member member) {
        jpaMemberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(int seq) {
        return jpaMemberRepository.findById(seq);
    }

    @Override
    public Member getReferenceById(int seq) {
        return jpaMemberRepository.getReferenceById(seq);
    }

    @Override
    public Boolean existsById(int seq) {
        return jpaMemberRepository.existsById(seq);
    }

    @Override
    public Boolean existsByUserId(String userId) {
        return jpaMemberRepository.existsByUserId(userId);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpaMemberRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsBySeqAndUserIdAndUsername(int seq, String userId, String username) {
        return jpaMemberRepository.existsBySeqAndUserIdAndUsername(seq, userId, username);
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        return jpaMemberRepository.findByUserId(userId);
    }

    @Override
    public Optional<Member> findByUserIdAndEmail(String userId, String email) {
        return jpaMemberRepository.findByUserIdAndEmail(userId, email);
    }
}