package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.dto.BranchDTO;
import reride.reride_backend.dto.EmployeeDTO;
import reride.reride_backend.entity.Branch;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.repository.BranchRepo;
import reride.reride_backend.repository.EmployeeRepo;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    BranchRepo branchRepo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmployeeRepo employeeRepo;

    public Branch addBranchService(String authHeader, Branch branchData) throws AccessDeniedException {
        String token = authHeader.substring(7);
        Long employeeId = jwtUtil.extractUserId(token);
        String employeeRole = jwtUtil.extractUserRole(token);

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee doesn't exist"));

        if (!("SUPER_ADMIN".equalsIgnoreCase(employeeRole))) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN can add a branch.");
        }

        // Duplicate checks
        if (branchRepo.existsByBranchGstNo(branchData.getBranchGstNo())) {
            throw new IllegalArgumentException("Branch with GST number " + branchData.getBranchGstNo() + " already exists");
        }

        if (branchRepo.existsByBranchPanNo(branchData.getBranchPanNo())) {
            throw new IllegalArgumentException("Branch with PAN number " + branchData.getBranchPanNo() + " already exists");
        }

        if (branchRepo.existsByBranchPhNo(branchData.getBranchPhNo())) {
            throw new IllegalArgumentException("Branch with phone number " + branchData.getBranchPhNo() + " already exists");
        }

        if (branchRepo.existsByBranchEmail(branchData.getBranchEmail())) {
            throw new IllegalArgumentException("Branch with email " + branchData.getBranchEmail() + " already exists");
        }

        // Save branch
        Branch branch = new Branch();
        branch.setBranchName(branchData.getBranchName());
        branch.setBranchAddress(branchData.getBranchAddress());
        branch.setBranchCity(branchData.getBranchCity());
        branch.setBranchArea(branchData.getBranchArea());
        branch.setBranchPinCode(branchData.getBranchPinCode());
        branch.setBranchGstNo(branchData.getBranchGstNo());
        branch.setBranchPanNo(branchData.getBranchPanNo());
        branch.setBranchOwnerName(branchData.getBranchOwnerName());
        branch.setBranchPhNo(branchData.getBranchPhNo());
        branch.setBranchEmail(branchData.getBranchEmail());

        return branchRepo.save(branch);
    }

    public List<BranchDTO> getBranchesService(String authHeader) throws AccessDeniedException {
        String token=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);

        Employee employee=employeeRepo.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee Doesn't exist"));
        if (!("SUPER_ADMIN".equalsIgnoreCase(employeeRole))) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN can view branch list.");
        }

        return branchRepo.findAll().stream().map(branch -> new BranchDTO(
                branch.getBranchId(),
                branch.getBranchName(),
                branch.getBranchAddress(),
                branch.getBranchCity(),
                branch.getBranchArea(),
                branch.getBranchPinCode(),
                branch.getBranchGstNo(),
                branch.getBranchPanNo(),
                branch.getBranchOwnerName(),
                branch.getBranchPhNo(),
                branch.getBranchEmail()
                )).toList();

    }


    public Optional<Branch> getBranchByIdService(String authHeader, Long branchId) throws AccessDeniedException {
        String token=authHeader.substring(7);
        Long employeeIdByToken=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);
        System.out.println("employeeId: "+branchId +" employeeRole: "+employeeRole);
        Employee employee=employeeRepo.findById(employeeIdByToken).orElseThrow(() -> new RuntimeException("Employee Doesn't exist"));
        if (!("SUPER_ADMIN".equalsIgnoreCase(employeeRole))) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN view branch list.");
        }
        return branchRepo.findById(branchId);
    }

    public Branch updateBranchService(String authHeader, Long branchId, Branch updatedBranch)
            throws AccessDeniedException {

        String token = authHeader.substring(7);
        String role = jwtUtil.extractUserRole(token);

        // Only SUPER_ADMIN can update branches
        if (!"SUPER_ADMIN".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN can update branch details.");
        }

        Branch existingBranch = branchRepo.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + branchId));

        // Update only non-null and non-empty fields
        if (updatedBranch.getBranchName() != null && !updatedBranch.getBranchName().isBlank()) {
            existingBranch.setBranchName(updatedBranch.getBranchName());
        }
        if (updatedBranch.getBranchAddress() != null && !updatedBranch.getBranchAddress().isBlank()) {
            existingBranch.setBranchAddress(updatedBranch.getBranchAddress());
        }
        if (updatedBranch.getBranchCity() != null && !updatedBranch.getBranchCity().isBlank()) {
            existingBranch.setBranchCity(updatedBranch.getBranchCity());
        }
        if (updatedBranch.getBranchArea() != null && !updatedBranch.getBranchArea().isBlank()) {
            existingBranch.setBranchArea(updatedBranch.getBranchArea());
        }
        if (updatedBranch.getBranchPinCode() != null && !updatedBranch.getBranchPinCode().isBlank()) {
            existingBranch.setBranchPinCode(updatedBranch.getBranchPinCode());
        }
        if (updatedBranch.getBranchGstNo() != null && !updatedBranch.getBranchGstNo().isBlank()) {
            existingBranch.setBranchGstNo(updatedBranch.getBranchGstNo());
        }
        if (updatedBranch.getBranchPanNo() != null && !updatedBranch.getBranchPanNo().isBlank()) {
            existingBranch.setBranchPanNo(updatedBranch.getBranchPanNo());
        }
        if (updatedBranch.getBranchOwnerName() != null && !updatedBranch.getBranchOwnerName().isBlank()) {
            existingBranch.setBranchOwnerName(updatedBranch.getBranchOwnerName());
        }
        if (updatedBranch.getBranchPhNo() != null && !updatedBranch.getBranchPhNo().isBlank()) {
            existingBranch.setBranchPhNo(updatedBranch.getBranchPhNo());
        }
        if (updatedBranch.getBranchEmail() != null && !updatedBranch.getBranchEmail().isBlank()) {
            existingBranch.setBranchEmail(updatedBranch.getBranchEmail());
        }

        return branchRepo.save(existingBranch);
    }

    public void deleteBranchService(String authHeader, Long branchId) throws AccessDeniedException {
        String token = authHeader.substring(7);
        String role = jwtUtil.extractUserRole(token);

        // Only SUPER_ADMIN can delete branches
        if (!"SUPER_ADMIN".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Access denied: Only SUPER_ADMIN can delete branches.");
        }

        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + branchId));

        branchRepo.delete(branch);
    }
}

