package state.admin.userManage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import state.admin.userManage.application.fasade.UserManage;
import state.admin.userManage.presentation.request.UserInfoRegisterRequest;
import state.admin.userManage.presentation.request.UserInfoRequest;
import state.admin.userManage.presentation.request.UserInfoUpdRequest;
import state.common.command.ResponseCommand;

import java.time.LocalDateTime;

@RequestMapping("/admin")
@Controller
public class AdminManageApi {

    private final UserManage userManage;

    public AdminManageApi(UserManage userManage) {
        this.userManage = userManage;
    }

    // TODO 사용자 조회

    // TODO 사용자 상세조회
    @PostMapping(value = "/userInfo",name = "사용자정보")
    public ResponseEntity<ResponseCommand> findUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        return null;
    }

    // TODO 사용자 등록
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
    
    // TODO 사용자 정보 수정
    @PostMapping(value = "/update", name = "회원정보수정")
    public ResponseEntity<ResponseCommand> update(@RequestBody UserInfoUpdRequest userInfoUpdRequest) {
        return null;
    }
    
    // TODO 사용자 탈퇴
    @PostMapping(value = "/delete", name = "회원탈퇴")
    public ResponseEntity<ResponseCommand> delete(@RequestBody UserInfoUpdRequest userInfoUpdRequest) {
        return null;
    }
}
