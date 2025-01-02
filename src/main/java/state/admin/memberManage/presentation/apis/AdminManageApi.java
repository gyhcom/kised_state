package state.admin.memberManage.presentation.apis;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import state.common.exception.ErrorCode;
import state.admin.memberManage.application.fasade.MemberManage;
import state.admin.memberManage.presentation.request.*;
import state.common.command.ResponseCommand;
import state.member.application.fasade.DepartmentManager;
import state.member.application.fasade.PositionManager;
import state.member.domain.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/dashboard/admin")
@Controller
public class AdminManageApi {

    private final MemberManage memberManage;
    private final DepartmentManager departmentManager;
    private final PositionManager positionManager;

    public AdminManageApi(MemberManage memberManage,
                          DepartmentManager departmentManager,
                          PositionManager positionManager) {
        this.memberManage = memberManage;
        this.departmentManager = departmentManager;
        this.positionManager = positionManager;
    }

    @GetMapping(value = "/memberList", name = "관리자(사용자 조회)")
    public String memberList(@ModelAttribute MemberMngListRequest memberMngListRequest, Model model) {
        List<Member> memberList = memberManage.findList(memberMngListRequest.getUsername());
        model.addAttribute("members",memberList);
        return "memberInfoList";
    }

    @GetMapping(value = "/memberInfo/memberInfoDetail/{userId}",name = "관리자(사용자 상세조회)")
    public ModelAndView findUserInfo(@PathVariable String userId, ModelAndView mv) {
        Member user = memberManage.findById(userId);
        mv.addObject("member", user);

        // 부서, 직위 세팅
        mv.addObject("dept", departmentManager.getReferenceById(user.getDepartmentCode()));
        mv.addObject("psit", positionManager.findById(user.getPositionCode()));

        mv.setViewName("memberInfoDetail");
        return mv;
    }

    @GetMapping(value = "/memberRegister", name = "관리자(사용자 회원가입 페이지 호출)")
    public String memberRegister() {
        return "memberInfoRegister";
    }

    @PostMapping(value = "/register" , name = "관리자 (사용자 회원가입)")
    public ResponseEntity<ResponseCommand> register(@RequestBody @Valid MemberMngRegisterRequest memberMngRegisterRequest) {
        memberManage.save(memberMngRegisterRequest.toCommand(memberMngRegisterRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(ErrorCode.OK.getErrorCode())
                        .message(ErrorCode.OK.getDescription())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
    @ResponseBody
    @PostMapping(value = "/update" , name = "관리자(사용자 회원정보수정)")
    public ResponseEntity<ResponseCommand> update(@RequestBody @Valid MemberMngUpdateRequest memberMngUpdateRequest) {
        memberManage.update(memberMngUpdateRequest.toCommand(memberMngUpdateRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(ErrorCode.OK.getErrorCode())
                        .message(ErrorCode.OK.getDescription())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
    @ResponseBody
    @PostMapping(value = "/delete", name = "관리자 (사용자 회원탈퇴)")
    public ResponseEntity<ResponseCommand> delete(@RequestBody MemberMngDeleteRequest memberMngDeleteRequest) {
        memberManage.delete(memberMngDeleteRequest.toCommand(memberMngDeleteRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(ErrorCode.OK.getErrorCode())
                        .message(ErrorCode.OK.getDescription())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
}
