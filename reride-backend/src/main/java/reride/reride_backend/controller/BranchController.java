package reride.reride_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.entity.Branch;
import reride.reride_backend.service.BranchService;

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
}
