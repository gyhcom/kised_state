package state.member.presentation.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import state.common.command.ResponseCommand;
import state.member.application.fasade.MemberManager;
import state.member.domain.entity.Member;
import state.member.presentation.request.MemberRegisterRequest;
import state.member.presentation.request.MemberUpdateRequest;
import state.member.presentation.response.MemberResponseCommand;
import state.member.domain.exception.MemberNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping("/member")
@Controller
public final class MemberAccountApi { //TODO 에러 처리 -> 클라이언트
    private final MemberManager memberManager;


    public MemberAccountApi(MemberManager memberManager) {
        this.memberManager = memberManager;
    }

    @ResponseBody
    @GetMapping("/memberInfo")
    public ResponseEntity<MemberResponseCommand> findUserInfo(@RequestBody MemberRegisterRequest memberRegisterRequest) {
        Optional<Member> user = memberManager.findById(memberRegisterRequest.getSeq());

        return new ResponseEntity<>(user.orElseThrow(MemberNotFoundException::new).toCommand(), HttpStatus.OK);
    }

    /* TODO
        이메일 인증으로 구현(본인 이메일로 인증)
    */
    @PostMapping("/register")
    public ResponseEntity<ResponseCommand> userRegister(@RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberManager.save(memberRegisterRequest.toCommand(memberRegisterRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    //TODO 논리적으로 삭제
    @PostMapping("/delete")
    public ResponseEntity<ResponseCommand> userDelete(@RequestBody MemberRegisterRequest memberRegisterRequest) {
        memberManager.delete(memberRegisterRequest.getSeq());
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseCommand> userUpdate(@RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberManager.update(memberUpdateRequest.toCommand(memberUpdateRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    //활동 로그 기록
    
}
