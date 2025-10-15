package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PutMapping("/addEmployee")
    public void addEmployee(@RequestBody Employee employeeData){
        employeeService.addEmployee(employeeData);
    }
}
