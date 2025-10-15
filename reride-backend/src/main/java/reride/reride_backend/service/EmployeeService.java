package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.dto.EmployeeDTO;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.repository.EmployeeRepo;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;


    public Employee addEmployee(Employee employeeData){
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
        employee.setAddedById(employeeData.getAddedById());
        return employeeRepo.save(employee);
    }

    public EmployeeDTO employeeLoginService(Employee employeeFormData) {
//        System.out.println(employeeFormData.getEmployeeEmail());
        return employeeRepo.findByEmployeeEmail(employeeFormData.getEmployeeEmail())
                .filter(employee -> passwordEncoder.matches(employeeFormData.getEmployeePassword(), employee.getEmployeePassword()))
                .map(employee -> {
                    String token = jwtUtil.generateToken(employee.getEmployeeEmail(), employee.getEmployeeId(),employee.getEmployeeRole());
                    return new EmployeeDTO(
                            token,
                            employee.getEmployeeId(),
                            employee.getEmployeeName(),
                            employee.getEmployeePhNo(),
                            employee.getEmployeeEmail(),
                            employee.getEmployeeRole(),
                            employee.getAddedById()
                    );
                })
                .orElseThrow(() -> new RuntimeException("Employee Login Failed"));
    }

}
