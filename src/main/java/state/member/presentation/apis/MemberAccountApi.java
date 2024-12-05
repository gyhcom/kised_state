package state.member.presentation.apis;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import state.common.command.ResponseCommand;
import state.member.application.fasade.MemberManager;
import state.member.domain.entity.Member;
import state.member.presentation.request.member.MemberDeleteRequest;
import state.member.presentation.request.member.MemberRegisterRequest;
import state.member.presentation.request.member.MemberUpdateRequest;
import state.member.presentation.response.MemberResponse;
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

    //TODO registerRequest와 성격이 맞지 않음. 수정 필요
    @ResponseBody
    @GetMapping("/memberInfo")
    public ResponseEntity<MemberResponse> findUserInfo(@RequestBody MemberRegisterRequest memberRegisterRequest) {
        Optional<Member> member = memberManager.findById(memberRegisterRequest.getSeq());
        return new ResponseEntity<>(member.get().toCommand(), HttpStatus.OK);
    }

    /* TODO
        이메일 인증으로 구현(본인 이메일로 인증)
    */
    @PostMapping("/register")
    public ResponseEntity<ResponseCommand> userRegister(@Valid @RequestBody MemberRegisterRequest memberRegisterRequest) {
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
    public ResponseEntity<ResponseCommand> userDelete(@RequestBody MemberDeleteRequest memberDeleteRequest) {
        memberManager.delete(
                memberDeleteRequest.getSeq(),
                memberDeleteRequest.getUserId(),
                memberDeleteRequest.getUsername()
        );
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseCommand> userUpdate(@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
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
