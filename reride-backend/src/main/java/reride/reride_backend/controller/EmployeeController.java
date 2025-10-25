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
    public ResponseEntity<String> addEmployee(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Employee employeeData
    ) {
        try {
            employeeService.addEmployee(authHeader, employeeData);
            return ResponseEntity.ok("Employee Successfully added");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PostMapping("/addAdmin/{branchId}")
    public ResponseEntity<String> addAdmin(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long branchId,
            @RequestBody Employee employeeData) throws AccessDeniedException {

        employeeService.addAdminToBranch(authHeader, branchId, employeeData);
        return ResponseEntity.ok("Admin added successfully");
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

    @GetMapping("/getDetails")
    public  ResponseEntity<EmployeeDTO> getEmployeeDetails (@RequestHeader("Authorization") String authHeader) throws AccessDeniedException{
        EmployeeDTO employee = employeeService.getEmployeeDetails(authHeader);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/getEmployeeByBranchId/{branchId}")
    public List<Employee> getEmployeeByBranchId(@RequestHeader("Authorization") String authHeader,@PathVariable Long branchId) throws AccessDeniedException {
        List<Employee> employee=employeeService.getEmployeeByBranchIdService(authHeader,branchId);
        return employee;
    }

    @PutMapping("/updateEmployee/{employeeId}")
    public ResponseEntity<String> updateEmployee(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long employeeId,
            @RequestBody Employee employeeData) throws AccessDeniedException {
        employeeService.updateEmployeeService(authHeader, employeeId, employeeData);
        return ResponseEntity.ok("Employee updated successfully");
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long employeeId) throws AccessDeniedException {
        employeeService.deleteEmployeeService(authHeader, employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }


}
