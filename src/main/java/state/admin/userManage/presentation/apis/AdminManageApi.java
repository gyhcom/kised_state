package state.admin.userManage.presentation.apis;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import state.common.exception.ErrorCode;
import state.admin.userManage.application.fasade.UserManage;
import state.admin.userManage.presentation.request.*;
import state.common.command.ResponseCommand;
import state.member.application.fasade.DepartmentManager;
import state.member.application.fasade.PositionManager;
import state.member.domain.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/dashboard/admin")
@Controller
public class AdminManageApi {

    private final UserManage userManage;
    private final DepartmentManager departmentManager;
    private final PositionManager positionManager;

    public AdminManageApi(UserManage userManage,
                          DepartmentManager departmentManager,
                          PositionManager positionManager) {
        this.userManage = userManage;
        this.departmentManager = departmentManager;
        this.positionManager = positionManager;
    }

    @GetMapping(value = "/userList", name = "관리자(사용자 조회)")
    public String userList(@ModelAttribute UserListRequest userListRequest, Model model) {
        List<Member> userList = userManage.findList(userListRequest.getUsername());
        model.addAttribute("members",userList);
        return "userInfoList";
    }

    @GetMapping(value = "/userInfo/userInfoDetail/{userId}",name = "관리자(사용자 상세조회)")
    public ModelAndView findUserInfo(@PathVariable String userId, ModelAndView mv) {
        Member user = userManage.findById(userId);
        mv.addObject("user", user);

        // 부서, 직위 세팅
        mv.addObject("dept", departmentManager.getReferenceById(user.getDepartmentCode()));
        mv.addObject("psit", positionManager.findById(user.getPositionCode()));

        mv.setViewName("userInfoDetail");
        return mv;
    }
    @PostMapping(value = "/register" , name = "관리자 (사용자 회원가입)")
    public ResponseEntity<ResponseCommand> register(@RequestBody @Valid UserInfoRegisterRequest userInfoRegisterRequest) {
        userManage.save(userInfoRegisterRequest.toCommand(userInfoRegisterRequest));
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
    public ResponseEntity<ResponseCommand> update(@RequestBody @Valid UserInfoUpdateRequest userInfoUpdateRequest) {
        userManage.update(userInfoUpdateRequest.toCommand(userInfoUpdateRequest));
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
    public ResponseEntity<ResponseCommand> delete(@RequestBody UserInfoDeleteRequest userInfoDeleteRequest) {
        userManage.delete(userInfoDeleteRequest.toCommand(userInfoDeleteRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(ErrorCode.OK.getErrorCode())
                        .message(ErrorCode.OK.getDescription())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
}
