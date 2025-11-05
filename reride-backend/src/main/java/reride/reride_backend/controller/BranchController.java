package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.dto.BranchDTO;
import reride.reride_backend.dto.BranchWebsiteDTO;
import reride.reride_backend.entity.Branch;
import reride.reride_backend.service.BranchService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    BranchService branchService;
// add branch
    @PostMapping("/addBranch")
    public ResponseEntity<String> addBranch(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Branch branchData) throws AccessDeniedException {

        branchService.addBranchService(authHeader, branchData);
        return ResponseEntity.ok("Branch Data Successfully Added");
    }
//get branch
    @GetMapping("/getBranches")
    public ResponseEntity<List<BranchDTO>> getBranches(@RequestHeader("Authorization") String authHeader) throws AccessDeniedException {
        List<BranchDTO> branch=branchService.getBranchesService(authHeader);
        return ResponseEntity.ok(branch);
    }

    //get branch
    @GetMapping("/website/getBranches")
    public ResponseEntity<List<BranchWebsiteDTO>> getBranchesWebsite() throws AccessDeniedException {
        List<BranchWebsiteDTO> branch=branchService.getBranchesWebsite();
        return ResponseEntity.ok(branch);
    }
//get branches by branch ID
    @GetMapping("/getBranches/{branchId}")
    public Optional<Branch> getBranchById(@RequestHeader("Authorization") String authHeader, @PathVariable long branchId) throws AccessDeniedException {
        return branchService.getBranchByIdService(authHeader,branchId);
    }
//Update branche by branch Id
    @PutMapping("/updateBranch/{branchId}")
    public ResponseEntity<String> updateBranch(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long branchId,
            @RequestBody Branch updatedBranch) throws AccessDeniedException {
        branchService.updateBranchService(authHeader, branchId, updatedBranch);
        return ResponseEntity.ok("Branch updated successfully");
    }
// delete Branch by branch ID
    @DeleteMapping("/deleteBranch/{branchId}")
    public ResponseEntity<String> deleteBranch(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long branchId) throws AccessDeniedException {
        branchService.deleteBranchService(authHeader, branchId);
        return ResponseEntity.ok("Branch deleted successfully");
    }

}
