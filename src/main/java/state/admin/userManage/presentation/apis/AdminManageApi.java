package state.admin.userManage.presentation.apis;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import state.common.exception.ErrorCode;
import state.admin.userManage.application.fasade.UserManage;
import state.admin.userManage.presentation.request.*;
import state.common.command.ResponseCommand;
import state.member.domain.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/dashboard/admin")
@Controller
public class AdminManageApi {

    private final UserManage userManage;

    public AdminManageApi(UserManage userManage) {
        this.userManage = userManage;
    }

    @GetMapping(value = "/userList", name = "사용자 조회")
    public String userList(@ModelAttribute UserListRequest userListRequest, Model model) {
        List<Member> userList = userManage.findList(userListRequest.getUsername());
        model.addAttribute("members",userList);
        return "userInfoList";
    }

    @GetMapping(value = "/userInfo/userInfoDetail/{userId}",name = "사용자 상세조회")
    public String findUserInfo(@PathVariable String userId, Model model) {
        Member user = userManage.findById(userId);
        model.addAttribute("user", user);
        return "userInfoDetail";
    }
    @PostMapping(value = "/register" , name = "회원가입")
    public ResponseEntity<ResponseCommand> register(@RequestBody @Valid UserInfoRegisterRequest userInfoRegisterRequest, Model model) {
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
    @PostMapping(value = "/update" , name = "회원정보수정")
    public ResponseEntity<ResponseCommand> update(@RequestBody @Valid UserInfoUpdateRequest userInfoUpdateRequest, Model model) {
        userManage.update(userInfoUpdateRequest.toCommand(userInfoUpdateRequest));
        model.addAttribute("message", "수정이 완료되었습니다");
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(ErrorCode.OK.getErrorCode())
                        .message(ErrorCode.OK.getDescription())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
    @ResponseBody
    @PostMapping(value = "/delete", name = "회원탈퇴")
    public ResponseEntity<ResponseCommand> delete(@ModelAttribute UserInfoDeleteRequest userInfoDeleteRequest) {
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
