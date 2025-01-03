package state.member.presentation.apis;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import state.admin.memberManage.application.common.exception.ApiException;
import state.common.command.ResponseCommand;
import state.common.exception.ErrorCode;
import state.member.application.fasade.DepartmentManager;
import state.member.application.fasade.MemberManager;
import state.member.application.fasade.PositionManager;
import state.member.domain.entity.Member;
import state.member.domain.exception.custom.MemberNotFoundException;
import state.member.presentation.request.member.MemberDeleteRequest;
import state.member.presentation.request.member.MemberModPasswordRequest;
import state.member.presentation.request.member.MemberRegisterRequest;
import state.member.presentation.request.member.MemberUpdateRequest;
import state.member.presentation.response.MemberResponse;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequestMapping("/member")
@Controller
public final class MemberAccountApi { //TODO 에러 처리 -> 클라이언트
    private final MemberManager memberManager;
    private final DepartmentManager departmentManager;
    private final PositionManager positionManager;


    public MemberAccountApi(
            MemberManager memberManager,
            DepartmentManager departmentManager,
            PositionManager positionManager) {
        this.memberManager = memberManager;
        this.departmentManager = departmentManager;
        this.positionManager = positionManager;
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
        //TODO 회원 정보가 수정된 후 session에 담겨있는 회원 정보도 수정해서 세팅


        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping("/member-profile")
    public ModelAndView userProfileForm(ModelAndView mv, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if( session.getAttribute("seq") == null ) throw new ApiException(ErrorCode.SERVER_ERROR);

        mv.addObject("member", memberManager.findById((int)session.getAttribute("seq")).get());
        mv.addObject("dept", departmentManager.getReferenceById(String.valueOf(session.getAttribute("deptCd"))));
        mv.addObject("psit", positionManager.findById(String.valueOf(session.getAttribute("psitCd"))));

        mv.setViewName("member-profile");
        return mv;
    }

    @GetMapping("update-password-form")
    public String updatePasswordForm() {
        return "update-password-form";
    }

    @PostMapping("/update-password")
    public ResponseEntity<ResponseCommand> updatePassword(@RequestBody MemberModPasswordRequest memberModPasswordRequest, HttpServletRequest request) {
        // session으로 사용자 ID, email 가져오기
        HttpSession session = request.getSession();
        if(String.valueOf(session.getAttribute("userId")).isBlank()) {
            throw new ApiException(ErrorCode.SERVER_ERROR, "userId가 존재하지 않습니다.");
        }

        if(String.valueOf(session.getAttribute("email")).isBlank()) {
            throw new ApiException(ErrorCode.SERVER_ERROR, "email이 존재하지 않습니다.");
        }

        memberManager.modifyPassword(
                session.getAttribute("userId").toString(),
                session.getAttribute("email").toString(),
                memberModPasswordRequest.toCommand(memberModPasswordRequest)
        );


        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
}
