package reride.reride_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.entity.TestRide;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.TestRideStatus;
import reride.reride_backend.repository.EmployeeRepo;
import reride.reride_backend.repository.InspectionRepo;
import reride.reride_backend.repository.TestRideRepository;
import reride.reride_backend.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestRideService {

    @Autowired
    TestRideRepository testRideRepository;

    @Autowired
    VehicleRepository vehicleRepo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmployeeRepo employeeRepo;

    // Customer books a test ride
    public TestRide bookTestRide(TestRide testRide) {
        Vehicle vehicle = vehicleRepo.findById(testRide.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        testRide.setVehicle(vehicle);
        testRide.setTestRideStatus(TestRideStatus.REQUESTED);
        return testRideRepository.save(testRide);
    }

    // Staff updates status (accept/reschedule)
    public TestRide updateTestRideStatus(String authHeader, TestRide testRideForm, Long testRideId, TestRideStatus status) {
        String token = authHeader.substring(7);
        Long employeeId = jwtUtil.extractUserId(token);
        String role = jwtUtil.extractUserRole(token);

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        TestRide testRide = testRideRepository.findById(testRideId)
                .orElseThrow(() -> new RuntimeException("Test Ride not found with ID: " + testRideId));

        testRide.setTestRideStatus(status);
        testRide.setTestRideDate(testRideForm.getTestRideDate());
        testRide.setTestRideTime(testRideForm.getTestRideTime());

        return testRideRepository.save(testRide);
    }

    // View all test rides
    public List<TestRide> getAllTestRides(String authHeader) {
        String token = authHeader.substring(7);
        Long employeeId = jwtUtil.extractUserId(token);

        employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        return testRideRepository.findAll();
    }

    // View test rides by status (like pending requests)
    public List<TestRide> getTestRidesByStatus(String authHeader, TestRideStatus status) {
        String token = authHeader.substring(7);
        Long employeeId = jwtUtil.extractUserId(token);
        String employeeRole = jwtUtil.extractUserRole(token);

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

        Long branchId = employee.getBranch().getBranchId();

        return testRideRepository.findByTestRideStatusAndVehicle_BranchId(status, branchId);
    }

}
