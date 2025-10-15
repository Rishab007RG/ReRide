package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.dto.EmployeeDTO;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/addEmployee")
    public ResponseEntity<String > addEmployee(@RequestBody Employee employeeData){
        employeeService.addEmployee(employeeData);
        return ResponseEntity.ok("Employee Successfully added");
    }

    @PostMapping("/login")
    public ResponseEntity<EmployeeDTO> employeeLoginController(@RequestBody Employee employeeFormData){
        EmployeeDTO response=employeeService.employeeLoginService(employeeFormData);
        return ResponseEntity.ok(response);
    }
}
