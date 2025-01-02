package state.admin.memberManage.presentation.request;

import lombok.Getter;
import lombok.Setter;
import state.admin.memberManage.application.command.MemberMngDeleteCommand;

@Getter
@Setter
public class MemberMngDeleteRequest {

    int seq;

    public MemberMngDeleteCommand toCommand(MemberMngDeleteRequest memberMngDeleteRequest) {
        return MemberMngDeleteCommand.builder()
                .seq(memberMngDeleteRequest.getSeq())
                .build();
    }
}
