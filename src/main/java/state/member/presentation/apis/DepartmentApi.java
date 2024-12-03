package state.member.presentation.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import state.member.application.fasade.DepartmentManager;
import state.member.domain.entity.Department;
import state.member.presentation.request.department.DepartmentInfoRequest;
import state.member.presentation.response.DepartmentResponse;

import java.util.List;

@RequestMapping("/dept")
@Controller
public class DepartmentApi {
    private final DepartmentManager departmentManager;
    public DepartmentApi(DepartmentManager departmentManager) {
        this.departmentManager = departmentManager;
    }
    @GetMapping("/deptInfo")
    public ResponseEntity<List<DepartmentResponse>> deptInfo() {
        return new ResponseEntity<>(new Department().toCommand(departmentManager.findAll()), HttpStatus.OK);
    }
}
