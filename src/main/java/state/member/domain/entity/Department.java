package state.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Setter
@Getter
@Table(name = "DEPARTMENT")
@Entity
public class Department {
    @Id
    @Column(name = "DEPT_CD")
    String departmentCode;

    @Column(name = "DEPT_NM")
    String departmentName;

    public Department() {}
    
    public Department(String departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }
}
