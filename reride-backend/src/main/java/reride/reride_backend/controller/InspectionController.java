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
// update inspection destails [PROCESSING, ACCEPT, RESCHEDULE, SUBMIT] This is where Staff handles request
    @PutMapping("updateInspectionStatus/{inspectionId}/status")
    public ResponseEntity<Inspection> updateInspectionStatus(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Inspection inspection,
            @PathVariable Long inspectionId,
            @RequestParam InspectionStatus status) {

        Inspection updated = inspectionService.updateInspectionStatus(authHeader,inspection,inspectionId, status);
        return ResponseEntity.ok(updated);
    }
}

