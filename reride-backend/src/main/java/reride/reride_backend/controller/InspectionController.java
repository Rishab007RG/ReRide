package reride.reride_backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.enums.WebsiteVisibility;
import reride.reride_backend.service.InspectionService;

import java.util.List;

@RestController
@RequestMapping("/inspection")
//@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501"})
public class InspectionController {

    @Autowired
    InspectionService inspectionService;

    //to add inspection details
    @PostMapping("/addInspection")
    public ResponseEntity<Inspection> addInspection(@RequestBody Inspection inspection){
        return ResponseEntity.ok(inspectionService.addInspection(inspection));
    }

    // get inspection details by inspection ID
    @GetMapping("/getInspection/{inspectionId}")
    public ResponseEntity<Inspection> getInspectionById(@PathVariable Long id) {
        return ResponseEntity.ok(inspectionService.getInspectionById(id));
    }

    // get inspection details
    @GetMapping("/getInspection")
    public ResponseEntity<List<Inspection>> getAllInspections() {
        return ResponseEntity.ok(inspectionService.getAllInspections());
    }

    //update inspection details
    @PutMapping("/updateInspection/{inspectionId}")
    public ResponseEntity<Inspection> updateInspection(@RequestHeader("Authorization") String authHeader, @RequestBody Inspection inspection, @PathVariable Long inspectionId){
        return ResponseEntity.ok(inspectionService.updateInspectionService(authHeader, inspection, inspectionId));
    }

    // update inspection details [PROCESSING, ACCEPT, RESCHEDULE, SUBMIT] This is where Staff handles request
    @PutMapping("updateInspectionStatus/{inspectionId}/status")
    public ResponseEntity<Inspection> updateInspectionStatus(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Inspection inspection,
            @PathVariable Long inspectionId,
            @RequestParam InspectionStatus status) {

        Inspection updated = inspectionService.updateInspectionStatus(authHeader,inspection,inspectionId, status);
        return ResponseEntity.ok(updated);
    }

    //Get inspection details using inspection status
    @GetMapping("/getInspectionDetailsByStatus/{inspectionStatus}")
    public ResponseEntity<List<Inspection>> getInspectionDetailsByStatus(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String inspectionStatus) {
        List<Inspection> getInspectionDetailsByStatus = inspectionService.getInspectionDetailsByStatusService(authHeader, inspectionStatus);
        return ResponseEntity.ok(getInspectionDetailsByStatus);
    }

    //Get inspection detail using inspection id
    @GetMapping("/getInspectionDetailsById/{inspectionId}/{inspectionStatus}")
    public ResponseEntity<Inspection> getInspectionDetailsById(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String inspectionStatus,
            @PathVariable Long inspectionId) {
        Inspection getInspectionDetailsByStatus = inspectionService.getInspectionDetailsByIdService(authHeader, inspectionStatus,inspectionId);
        return ResponseEntity.ok(getInspectionDetailsByStatus);
    }

    //update the price and images by admin
    @PatchMapping(value = "/update/{vehicleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateVehicle(
            @PathVariable Long vehicleId,
            @RequestParam(required = false) String vehicleOutLetPrice,
            @RequestParam(required = false) String vehicleMileage,
            @RequestParam(required = false) WebsiteVisibility websiteVisibility,
            @RequestParam(required = false) MultipartFile[] vehicleImages,
            @RequestHeader("Authorization") String authHeader
    ) throws JsonProcessingException {

        Vehicle vehicle = inspectionService.updateVehicleDetails(
                vehicleId,
                vehicleOutLetPrice,
                vehicleMileage,
                websiteVisibility,
                vehicleImages,
                authHeader
        );

        return ResponseEntity.ok(vehicle);
    }

}

