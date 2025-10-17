package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.repository.EmployeeRepo;

@RestController
public class Register {

    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/superAdminRegister")
    public void superAdminRegister(@RequestBody Employee employeeData){
        if(employeeRepo.findByEmployeeEmail(employeeData.getEmployeeEmail()).isPresent()){
            throw  new RuntimeException("Employee already exist");
        }
        Employee employee=new Employee();
        employee.setEmployeeName(employeeData.getEmployeeName());
        employee.setEmployeeEmail(employeeData.getEmployeeEmail());
        employee.setEmployeePhNo(employeeData.getEmployeePhNo());
        employee.setEmployeeRole(employeeData.getEmployeeRole());
        String encryptPassword=passwordEncoder.encode(employeeData.getEmployeePassword());
        employee.setEmployeePassword(encryptPassword);
        employeeRepo.save(employee);
    }
}
