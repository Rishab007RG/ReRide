package reride.reride_backend.dto;

import lombok.Data;

@Data
public class BranchWebsiteDTO {
    private Long branchId;
    private String branchArea;

    public BranchWebsiteDTO(Long branchId, String branchArea) {
        this.branchId = branchId;
        this.branchArea = branchArea;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchArea() {
        return branchArea;
    }

    public void setBranchArea(String branchArea) {
        this.branchArea = branchArea;
    }
}
