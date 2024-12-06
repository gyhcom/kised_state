package state.admin.userManage.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import state.admin.userManage.application.common.api.Api;
import state.common.exception.ErrorCode;
import state.admin.userManage.presentation.response.UserResponse;
import state.admin.userManage.application.fasade.UserManage;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.presentation.request.*;
import state.common.command.ResponseCommand;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/kised_state/admin")
@Controller
public class AdminManageApi {

    private final UserManage userManage;

    public AdminManageApi(UserManage userManage) {
        this.userManage = userManage;
    }
    @GetMapping("/registerTest")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "index";
    }

    @GetMapping(value = "/userList", name = "사용자 조회")
    public ResponseEntity<List<User>> userList(@RequestBody UserListRequest userListRequest) {
        List<User> userList = userManage.findList(userListRequest.getUserNm());
        return ResponseEntity.ok(userList);
    }

    @ResponseBody
    @GetMapping(value = "/userInfo",name = "사용자 상세조회")
    public Api<UserResponse> findUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        User user = userManage.findById(userInfoRequest.getSeq());
        return Api.OK(user.toCommand());
    }
    @PostMapping(value = "/register" , name = "회원가입")
    public ResponseEntity<ResponseCommand> register(@ModelAttribute @Valid UserInfoRegisterRequest userInfoRegisterRequest, Model model) {
        userManage.save(userInfoRegisterRequest.toCommand(userInfoRegisterRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(ErrorCode.OK.getErrorCode())
                        .message(ErrorCode.OK.getDescription())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(value = "/update" , name = "회원정보수정")
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

    @PostMapping(value = "/delete", name = "회원탈퇴")
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
