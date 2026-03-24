package reride.reride_backend.dto;

import lombok.Data;
import reride.reride_backend.entity.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TestRideRequestDTO {

    private String testRideCustomerName;

    private String testRideCustomerEmail;

    private String testRideCustomerPhoneNo;

    private LocalDate testRideDate;

    private LocalTime testRideTime;

    private Long branchId;

    private Vehicle vehicle;


    public String getTestRideCustomerName() {
        return testRideCustomerName;
    }

    public void setTestRideCustomerName(String testRideCustomerName) {
        this.testRideCustomerName = testRideCustomerName;
    }

    public String getTestRideCustomerEmail() {
        return testRideCustomerEmail;
    }

    public void setTestRideCustomerEmail(String testRideCustomerEmail) {
        this.testRideCustomerEmail = testRideCustomerEmail;
    }

    public String getTestRideCustomerPhoneNo() {
        return testRideCustomerPhoneNo;
    }

    public void setTestRideCustomerPhoneNo(String testRideCustomerPhoneNo) {
        this.testRideCustomerPhoneNo = testRideCustomerPhoneNo;
    }

    public LocalDate getTestRideDate() {
        return testRideDate;
    }

    public void setTestRideDate(LocalDate testRideDate) {
        this.testRideDate = testRideDate;
    }

    public LocalTime getTestRideTime() {
        return testRideTime;
    }

    public void setTestRideTime(LocalTime testRideTime) {
        this.testRideTime = testRideTime;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
