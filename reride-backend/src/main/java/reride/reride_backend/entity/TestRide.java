package reride.reride_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import reride.reride_backend.enums.TestRideStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TestRide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testRideId;

    private String testRideCustomerName;

    private String testRideCustomerEmail;

    private String testRideCustomerPhoneNo;

    private LocalDate testRideDate;

    private LocalTime testRideTime;

    @Enumerated(EnumType.STRING)
    private TestRideStatus testRideStatus = TestRideStatus.REQUESTED;

    // Optional: link this test ride to a specific vehicle
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


    public Long getTestRideId() {
        return testRideId;
    }

    public void setTestRideId(Long testRideId) {
        this.testRideId = testRideId;
    }

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

    public TestRideStatus getTestRideStatus() {
        return testRideStatus;
    }

    public void setTestRideStatus(TestRideStatus testRideStatus) {
        this.testRideStatus = testRideStatus;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
