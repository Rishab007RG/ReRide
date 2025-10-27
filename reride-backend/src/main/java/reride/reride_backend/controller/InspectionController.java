package reride.reride_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.service.InspectionService;

import java.util.List;

@RestController
@RequestMapping("/inspection")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501"})

public class InspectionController {

    @Autowired
    InspectionService inspectionService;


    @PostMapping("/addInspection")
    public ResponseEntity<Inspection> addInspection(@RequestBody Inspection inspection){
        return ResponseEntity.ok(inspectionService.addInspection(inspection));
    }

    @GetMapping("/getInspection/{inspectionId}")
    public ResponseEntity<Inspection> getInspectionById(@PathVariable Long id) {
        return ResponseEntity.ok(inspectionService.getInspectionById(id));
    }

    @GetMapping("/getInspection")
    public ResponseEntity<List<Inspection>> getAllInspections() {
        return ResponseEntity.ok(inspectionService.getAllInspections());
    }

    @PutMapping("/updateInspection/{inspectionId}")
    public ResponseEntity<Inspection> updateInspection(@RequestHeader("Authorization") String authHeader, @RequestBody Inspection inspection, @PathVariable Long inspectionId){
        return ResponseEntity.ok(inspectionService.updateInspectionService(authHeader, inspection, inspectionId));
    }

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
}

