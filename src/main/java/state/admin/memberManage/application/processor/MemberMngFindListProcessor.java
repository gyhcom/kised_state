package state.admin.memberManage.application.processor;

import org.springframework.stereotype.Component;
import state.admin.memberManage.domain.repository.MemberMngRepository;
import state.member.domain.entity.Member;

import java.util.List;

@Component
public class MemberMngFindListProcessor {

    private final MemberMngRepository memberMngRepository;

    public MemberMngFindListProcessor(MemberMngRepository memberMngRepository) {
        this.memberMngRepository = memberMngRepository;
    }

    public List<Member> execute(String username) {
        List<Member> userList;
        if(username != null) {
            userList = memberMngRepository.findUserList(username);
        } else {
            userList = memberMngRepository.findAll();
        }
        return userList;
    }
}
