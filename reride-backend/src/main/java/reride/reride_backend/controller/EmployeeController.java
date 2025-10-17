package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.dto.EmployeeDTO;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.service.EmployeeService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @PostMapping("/login")
    public ResponseEntity<EmployeeDTO> employeeLoginController(@RequestBody Employee employeeFormData){
        EmployeeDTO response=employeeService.employeeLoginService(employeeFormData);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String > addEmployee(@RequestHeader("Authorization") String authHeader, @RequestBody Employee employeeData){
        employeeService.addEmployee(authHeader,employeeData);
        return ResponseEntity.ok("Employee Successfully added");
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestHeader("Authorization") String authHeader) throws AccessDeniedException {
        List<EmployeeDTO> employee=employeeService.getEmployeeService(authHeader);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/getEmployee/{employeeId}")
    public Optional<Employee> getEmployeeById(@RequestHeader("Authorization") String authHeader, @PathVariable Long employeeId) throws AccessDeniedException {
        return employeeService.getEmployeeByIdService(authHeader,employeeId);
    }

}
