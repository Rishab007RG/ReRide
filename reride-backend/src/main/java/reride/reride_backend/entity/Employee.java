package reride.reride_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import reride.reride_backend.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false)
    private String employeePhNo;

    @Column(nullable = false,unique = true)
    private String employeeEmail;

    @Column(nullable = false)
    private String employeePassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role employeeRole;

    @ManyToOne
    private Employee addedById;

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

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
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
