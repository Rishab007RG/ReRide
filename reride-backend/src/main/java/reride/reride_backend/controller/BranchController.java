package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.dto.BranchDTO;
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

    @PostMapping("/addBranch")
    public ResponseEntity<String> addBranch(@RequestBody Branch branchData){
        branchService.addBranchService(branchData);
        return  ResponseEntity.ok("Branch Data Successfully Added");
    }

    @GetMapping("/getBranches")
    public ResponseEntity<List<BranchDTO>> getBranches(@RequestHeader("Authorization") String authHeader) throws AccessDeniedException {
        List<BranchDTO> branch=branchService.getBranchesService(authHeader);
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/getBranches/{branchId}")
    public Optional<Branch> getBranchById(@RequestHeader("Authorization") String authHeader, @PathVariable long branchId) throws AccessDeniedException {
        return branchService.getBranchByIdService(authHeader,branchId);
    }
}
