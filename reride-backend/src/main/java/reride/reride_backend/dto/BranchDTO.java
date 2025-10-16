package reride.reride_backend.dto;


import lombok.Data;

@Data
public class BranchDTO {
    private Long branchId;
    private String branchName;
    private String branchAddress;
    private String branchCity;
    private String branchArea;
    private String branchPinCode;
    private String branchGstNo;
    private String branchPanNo;
    private String branchOwnerName;
    private String branchPhNo;
    private String branchEmail;

    public BranchDTO(Long branchId, String branchName, String branchAddress, String branchCity, String branchArea, String branchPinCode, String branchGstNo, String branchPanNo, String branchOwnerName, String branchPhNo, String branchEmail) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.branchCity = branchCity;
        this.branchArea = branchArea;
        this.branchPinCode = branchPinCode;
        this.branchGstNo = branchGstNo;
        this.branchPanNo = branchPanNo;
        this.branchOwnerName = branchOwnerName;
        this.branchPhNo = branchPhNo;
        this.branchEmail = branchEmail;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public String getBranchArea() {
        return branchArea;
    }

    public void setBranchArea(String branchArea) {
        this.branchArea = branchArea;
    }

    public String getBranchPinCode() {
        return branchPinCode;
    }

    public void setBranchPinCode(String branchPinCode) {
        this.branchPinCode = branchPinCode;
    }

    public String getBranchGstNo() {
        return branchGstNo;
    }

    public void setBranchGstNo(String branchGstNo) {
        this.branchGstNo = branchGstNo;
    }

    public String getBranchPanNo() {
        return branchPanNo;
    }

    public void setBranchPanNo(String branchPanNo) {
        this.branchPanNo = branchPanNo;
    }

    public String getBranchOwnerName() {
        return branchOwnerName;
    }

    public void setBranchOwnerName(String branchOwnerName) {
        this.branchOwnerName = branchOwnerName;
    }

    public String getBranchPhNo() {
        return branchPhNo;
    }

    public void setBranchPhNo(String branchPhNo) {
        this.branchPhNo = branchPhNo;
    }

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }
}
