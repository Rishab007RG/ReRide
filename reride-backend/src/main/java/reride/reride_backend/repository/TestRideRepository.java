package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.TestRide;
import reride.reride_backend.enums.TestRideStatus;

import java.util.List;

@Repository
public interface TestRideRepository extends JpaRepository<TestRide, Long> {

    // Fetch test rides by status (e.g. REQUESTED, ACCEPTED)
    List<TestRide> findByTestRideStatus(TestRideStatus testRideStatus);

    // Fetch all test rides for a specific vehicle
    List<TestRide> findByVehicle_VehicleId(Long vehicleId);
}
