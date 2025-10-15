package reride.reride_backend.dto;

import lombok.Data;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.enums.Role;

@Data
public class EmployeeDTO {
    private String token;
    private Long employeeId;
    private String employeeName;
    private String employeePhNo;
    private String employeeEmail;
    private Role employeeRole;
    private Employee addedById;

    public EmployeeDTO(String token, Long employeeId, String employeeName, String employeePhNo, String employeeEmail, Role employeeRole, Employee addedById) {
        this.token = token;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeePhNo = employeePhNo;
        this.employeeEmail = employeeEmail;
        this.employeeRole = employeeRole;
        this.addedById = addedById;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePhNo() {
        return employeePhNo;
    }

    public void setEmployeePhNo(String employeePhNo) {
        this.employeePhNo = employeePhNo;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Role getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(Role employeeRole) {
        this.employeeRole = employeeRole;
    }

    public Employee getAddedById() {
        return addedById;
    }

    public void setAddedById(Employee addedById) {
        this.addedById = addedById;
    }
}
