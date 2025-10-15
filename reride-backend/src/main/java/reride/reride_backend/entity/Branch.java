package reride.reride_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String branchAddress;

    @Column(nullable = false)
    private String branchCity;

    @Column(nullable = false)
    private String branchArea;

    @Column(nullable = false)
    private String branchPinCode;

//    @Pattern(
//            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
//            message = "Invalid GST number format"
//    )
    @Column(nullable = false,length = 15,unique = true)
    private String branchGstNo;

//    @NotBlank(message = "GST number cannot be empty")
//    @Pattern(
//            regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$",
//            message = "Invalid GST number format (example: 29ABCDE1234F2Z5)"
//    )
    @Column(nullable = false,length = 15,unique = true)
    private String branchPanNo;

    @Column(nullable = false)
    private String branchOwnerName;

    @Column(nullable = false,unique = true, length = 10)
    private String  branchPhNo;

    @Column(nullable = false,unique = true)
    private String branchEmail;


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
