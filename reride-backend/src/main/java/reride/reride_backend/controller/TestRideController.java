package reride.reride_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.entity.TestRide;
import reride.reride_backend.enums.TestRideStatus;
import reride.reride_backend.service.TestRideService;

import java.util.List;

@RestController
@RequestMapping("/testRide")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TestRideController {

    private TestRideService testRideService;

    // Customer books a test ride
    @PostMapping("/bookTestRide")
    public ResponseEntity<TestRide> bookTestRide(@RequestBody TestRide testRide) {
        return ResponseEntity.ok(testRideService.bookTestRide(testRide));
    }

    // Staff updates the test ride status (accept or reschedule)
    @PutMapping("/updateTestRideStatus/{testRideId}/status")
    public ResponseEntity<TestRide> updateTestRideStatus(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody TestRide testRideForm,
            @PathVariable Long testRideId,
            @RequestParam TestRideStatus testRideStatus) {

        TestRide updated = testRideService.updateTestRideStatus(authHeader, testRideForm, testRideId, testRideStatus);
        return ResponseEntity.ok(updated);
    }

    // View all test rides
    @GetMapping("/all")
    public ResponseEntity<List<TestRide>> getAllTestRides(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(testRideService.getAllTestRides(authHeader));
    }

    // Filter by status (e.g., REQUESTED)
    @GetMapping("/status/{testRideStatus}")
    public ResponseEntity<List<TestRide>> getTestRidesByStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable TestRideStatus testRideStatus) {
        return ResponseEntity.ok(testRideService.getTestRidesByStatus(authHeader, testRideStatus));
    }
}
