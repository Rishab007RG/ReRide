package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.dto.EmployeeDTO;
import reride.reride_backend.entity.Branch;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.enums.Role;
import reride.reride_backend.repository.BranchRepo;
import reride.reride_backend.repository.EmployeeRepo;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BranchRepo branchRepo;


    public Employee addEmployee(String authHeader,Employee employeeData){
        String token = authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);
        if(employeeRepo.findByEmployeeEmail(employeeData.getEmployeeEmail()).isPresent()){
            throw  new RuntimeException("Employee already exist");
        }

        Branch branch = null;

        if (employeeData.getEmployeeRole() != Role.SUPER_ADMIN) {
            if (employeeData.getBranch() == null || employeeData.getBranch().getBranchId() == null) {
                throw new IllegalArgumentException("Branch information is required for ADMIN and STAFF.");
            }
            branch = branchRepo.findById(employeeData.getBranch().getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + employeeData.getBranch().getBranchId()));
        }

//        Branch branch = branchRepo.findById(employeeData.getBranch().getBranchId())
//                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + employeeData.getBranch().getBranchId()));

        // Fetch AddedBy Employee entity
        Employee addedBy = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("AddedBy employee not found with ID: " + employeeId));

        Employee employee=new Employee();
        employee.setEmployeeName(employeeData.getEmployeeName());
        employee.setEmployeeEmail(employeeData.getEmployeeEmail());
        employee.setEmployeePhNo(employeeData.getEmployeePhNo());
        employee.setEmployeeRole(employeeData.getEmployeeRole());
        String encryptPassword=passwordEncoder.encode(employeeData.getEmployeePassword());
        employee.setEmployeePassword(encryptPassword);
        employee.setBranch(branch);
        employee.setAddedById(addedBy);
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

    public List<EmployeeDTO> getEmployeeService(String authHeader) throws AccessDeniedException {
        String jwt=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(jwt);
        String employeeRole=jwtUtil.extractUserRole(jwt);
        System.out.println("employeeId: "+employeeId +" employeeRole: "+employeeRole);
        if (!("SUPER_ADMIN".equalsIgnoreCase(employeeRole) || "ADMIN".equalsIgnoreCase(employeeRole))) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN and ADMIN can view employee list.");
        }
        return employeeRepo.findAll().stream()
                .map(employee -> new EmployeeDTO(
                        null,
                        employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getEmployeePhNo(),
                        employee.getEmployeeEmail(),
                        employee.getEmployeeRole(),
                        employee.getAddedById()
                )).toList();
    }

    public Optional<Employee> getEmployeeByIdService(String authHeader, Long employeeId) throws AccessDeniedException {
        String jwt=authHeader.substring(7);
        Long employeeIdByToken=jwtUtil.extractUserId(jwt);
        String employeeRole=jwtUtil.extractUserRole(jwt);
        System.out.println("employeeId: "+employeeId +" employeeRole: "+employeeRole);
        Employee employee=employeeRepo.findById(employeeIdByToken).orElseThrow(() -> new RuntimeException("Employee Doesn't exist"));
        if (!("SUPER_ADMIN".equalsIgnoreCase(employeeRole) || "ADMIN".equalsIgnoreCase(employeeRole))) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN and ADMIN can view employee list.");
        }
        return employeeRepo.findById(employeeId);
    }

    public EmployeeDTO getEmployeeDetails(String authHeader) {
        String jwt=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(jwt);
        String employeeRole=jwtUtil.extractUserRole(jwt);
        System.out.println("employeeId: "+employeeId +" employeeRole: "+employeeRole);
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        EmployeeDTO dto = new EmployeeDTO(
                null, // token
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getEmployeePhNo(),
                employee.getEmployeeEmail(),
                employee.getEmployeeRole(),
                null
        );
        return dto;
    }
}
