package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.repository.EmployeeRepo;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public void addEmployee(Employee employeeData){
        Employee employee=new Employee();
        employee.setEmployeeName(employeeData.getEmployeeName());
        employee.setEmployeeEmail(employeeData.getEmployeeEmail());
        employee.setEmployeePhNo(employeeData.getEmployeePhNo());
        employee.setEmployeeRole(employeeData.getEmployeeRole());
        employee.setEmployeePassword(employeeData.getEmployeePassword());
        employee.setAddedById(employeeData.getAddedById());
        employeeRepo.save(employee);

    }
}
