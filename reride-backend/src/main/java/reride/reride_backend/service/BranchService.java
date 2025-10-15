package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reride.reride_backend.entity.Branch;
import reride.reride_backend.repository.BranchRepo;

@Service
public class BranchService {
    @Autowired
    BranchRepo branchRepo;

    public Branch addBranchService(Branch branchData) {

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
}

