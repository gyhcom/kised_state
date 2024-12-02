package state.admin.userManage.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import state.admin.userManage.application.command.UserResponseCommand;
import state.admin.userManage.application.fasade.UserManage;
import state.admin.userManage.domain.entity.User;
import state.admin.userManage.presentation.request.*;
import state.common.command.ResponseCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class AdminManageApi {

    private final UserManage userManage;

    public AdminManageApi(UserManage userManage) {
        this.userManage = userManage;
    }

    @GetMapping(value = "/userList", name = "사용자 조회")
    public ResponseEntity<List<User>> userList(@RequestBody UserListRequest userListRequest) {
        List<User> userList = userManage.findList(userListRequest);
        return ResponseEntity.ok(userList);
    }

    @ResponseBody
    @GetMapping(value = "/userInfo",name = "사용자 상세조회")
    public ResponseEntity<UserResponseCommand> findUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        Optional<User> user = userManage.findById(userInfoRequest.getSeq());

        return new ResponseEntity<>(user.orElseThrow(NullPointerException::new).toCommand(), HttpStatus.OK);
    }

    @PostMapping(value = "/register" , name = "회원가입")
    public ResponseEntity<ResponseCommand> register(@RequestBody UserInfoRegisterRequest userInfoRegisterRequest) {
        userManage.save(userInfoRegisterRequest.toCommand(userInfoRegisterRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(value = "/update" , name = "회원정보수정")
    public ResponseEntity<ResponseCommand> update(@RequestBody UserInfoUpdateRequest userInfoUpdateRequest) {
        userManage.update(userInfoUpdateRequest.toCommand(userInfoUpdateRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(value = "/delete", name = "회원탈퇴")
    public ResponseEntity<ResponseCommand> delete(@RequestBody UserInfoDeleteRequest userInfoDeleteRequest) {
        userManage.delete(userInfoDeleteRequest.toCommand(userInfoDeleteRequest));
        return new ResponseEntity<>(
                ResponseCommand.builder()
                        .code(200)
                        .message("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.OK
        );
    }
}
