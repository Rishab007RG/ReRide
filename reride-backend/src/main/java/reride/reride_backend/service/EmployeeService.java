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


    public Employee addEmployee(String authHeader, Employee employeeData) throws AccessDeniedException {
        String token = authHeader.substring(7);
        Long requesterId = jwtUtil.extractUserId(token);
        String requesterRole = jwtUtil.extractUserRole(token);

        // Fetch requester info
        Employee requester = employeeRepo.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        // Only ADMIN can use this endpoint
        if (!"ADMIN".equalsIgnoreCase(requesterRole)) {
            throw new AccessDeniedException("Only ADMIN can add employees using this endpoint.");
        }


        // Ensure ADMIN has a branch assigned
        Branch branch = Optional.ofNullable(requester.getBranch())
                .orElseThrow(() -> new IllegalArgumentException("Admin must be assigned to a branch."));

        // Prevent duplicate email
        if (employeeRepo.findByEmployeeEmail(employeeData.getEmployeeEmail()).isPresent()) {
            throw new RuntimeException("Employee already exists with this email");
        }

        // Save new STAFF employee
        Employee employee = new Employee();
        employee.setEmployeeName(employeeData.getEmployeeName());
        employee.setEmployeeEmail(employeeData.getEmployeeEmail());
        employee.setEmployeePhNo(employeeData.getEmployeePhNo());
        employee.setEmployeeRole(Role.STAFF);
        employee.setEmployeePassword(passwordEncoder.encode(employeeData.getEmployeePassword()));
        employee.setBranch(branch);
        employee.setAddedById(requester);

        return employeeRepo.save(employee);
    }

    public Employee addEmployeeToBranchService(String authHeader, Long branchId, Employee employeeData) throws AccessDeniedException {
        String token = authHeader.substring(7);
        Long requesterId = jwtUtil.extractUserId(token);
        String requesterRole = jwtUtil.extractUserRole(token);

        if (!"SUPER_ADMIN".equalsIgnoreCase(requesterRole)) {
            throw new AccessDeniedException("Only SUPER_ADMIN can add ADMINs.");
        }

        // ADMIN can only add STAFF
        if (employeeData.getEmployeeRole() != Role.ADMIN) {
            throw new AccessDeniedException("SUPERADMIN can only add ADMIN.");
        }

        if (employeeRepo.findByEmployeeEmail(employeeData.getEmployeeEmail()).isPresent()) {
            throw new RuntimeException("Employee already exists with this email");
        }

        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + branchId));

        Employee requester = employeeRepo.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        Employee employee = new Employee();
        employee.setEmployeeName(employeeData.getEmployeeName());
        employee.setEmployeeEmail(employeeData.getEmployeeEmail());
        employee.setEmployeePhNo(employeeData.getEmployeePhNo());
        employee.setEmployeeRole(Role.ADMIN);
        employee.setEmployeePassword(passwordEncoder.encode(employeeData.getEmployeePassword()));
        employee.setBranch(branch);
        employee.setAddedById(requester);

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
        if (!("ADMIN".equalsIgnoreCase(employeeRole))) {
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

    public List<Employee> getEmployeeByBranchIdService(String authHeader, Long branchId) throws AccessDeniedException {
        String jwt = authHeader.substring(7);
        Long employeeIdByToken = jwtUtil.extractUserId(jwt);
        String employeeRole = jwtUtil.extractUserRole(jwt);

        Employee employee = employeeRepo.findById(employeeIdByToken)
                .orElseThrow(() -> new RuntimeException("Employee Doesn't exist"));

        if (!("SUPER_ADMIN".equalsIgnoreCase(employeeRole) || "ADMIN".equalsIgnoreCase(employeeRole))) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN and ADMIN can view employee list.");
        }

        // SUPER_ADMIN -> Fetch only ADMINs under that branch
        if ("SUPER_ADMIN".equalsIgnoreCase(employeeRole)) {
            return employeeRepo.findByBranch_BranchIdAndRole(branchId);
        }

        // ADMIN -> Fetch all employees in their own branch
        else if ("ADMIN".equalsIgnoreCase(employeeRole)) {
            return employeeRepo.findByBranch_BranchId(branchId);
        }

        // Fallback (should not reach here)
        throw new AccessDeniedException("Unauthorized access.");
    }

    public Employee updateEmployeeService(String authHeader, Long employeeId, Employee updatedData)
            throws AccessDeniedException {
        String token = authHeader.substring(7);
        Long requesterId = jwtUtil.extractUserId(token);
        String requesterRole = jwtUtil.extractUserRole(token);

        Employee requester = employeeRepo.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        Employee existing = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        // --- Role-based logic ---
        if ("SUPER_ADMIN".equalsIgnoreCase(requesterRole)) {
            if (existing.getEmployeeRole() != Role.ADMIN) {
                throw new AccessDeniedException("SUPER_ADMIN can only update ADMIN employees.");
            }
        } else if ("ADMIN".equalsIgnoreCase(requesterRole)) {
            if (existing.getEmployeeRole() != Role.STAFF && existing.getEmployeeRole()!=Role.ADMIN) {
                throw new AccessDeniedException("ADMIN can only update STAFF employees.");
            }
            if (!existing.getBranch().getBranchId().equals(requester.getBranch().getBranchId())) {
                throw new AccessDeniedException("ADMIN can only update STAFF within their own branch.");
            }
        } else {
            throw new AccessDeniedException("Access denied: Invalid role for updating employee.");
        }

        // Apply updates
        if (updatedData.getEmployeeName() != null)
            existing.setEmployeeName(updatedData.getEmployeeName());
        if (updatedData.getEmployeePhNo() != null)
            existing.setEmployeePhNo(updatedData.getEmployeePhNo());
        if (updatedData.getEmployeeEmail() != null)
            existing.setEmployeeEmail(updatedData.getEmployeeEmail());
        if (updatedData.getEmployeePassword() != null && !updatedData.getEmployeePassword().isBlank())
            existing.setEmployeePassword(passwordEncoder.encode(updatedData.getEmployeePassword()));

        return employeeRepo.save(existing);
    }

    public void deleteEmployeeService(String authHeader, Long employeeId) throws AccessDeniedException {
        String token = authHeader.substring(7);
        Long requesterId = jwtUtil.extractUserId(token);
        String requesterRole = jwtUtil.extractUserRole(token);

        Employee requester = employeeRepo.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("Requester not found"));

        Employee target = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        if(requesterId==employeeId){
            throw new RuntimeException("Self deletion is not allowed");
        }
        // --- Role-based logic ---
        if ("SUPER_ADMIN".equalsIgnoreCase(requesterRole)) {
            if (target.getEmployeeRole() != Role.ADMIN) {
                throw new AccessDeniedException("SUPER_ADMIN can only delete ADMIN employees.");
            }
        } else if ("ADMIN".equalsIgnoreCase(requesterRole)) {
            if (target.getEmployeeRole() != Role.STAFF) {
                throw new AccessDeniedException("ADMIN can only delete STAFF employees.");
            }
            if (!target.getBranch().getBranchId().equals(requester.getBranch().getBranchId())) {
                throw new AccessDeniedException("ADMIN can only delete STAFF within their own branch.");
            }
        } else {
            throw new AccessDeniedException("Access denied: Invalid role for deleting employee.");
        }

        employeeRepo.delete(target);
    }
}
