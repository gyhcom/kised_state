package state.member.presentation.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import state.member.presentation.request.department.DepartmentInfoRequest;

@RequestMapping("/dept")
@Controller
public class DepartmentApi {
    @GetMapping("/deptInfo")
    public ResponseEntity<?> deptInfo(@RequestBody DepartmentInfoRequest departmentInfoRequest) {
        return null;
    }
}
