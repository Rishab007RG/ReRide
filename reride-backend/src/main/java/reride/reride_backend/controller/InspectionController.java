package reride.reride_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.service.InspectionService;

@RestController
@RequestMapping("/inspection")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501"})

public class InspectionController {

    @Autowired
    InspectionService inspectionService;


    @PostMapping("/addInspection")
    public ResponseEntity<reride.reride_backend.entity.Inspection> addUsers(@RequestBody Inspection inspection){
        return ResponseEntity.ok(inspectionService.addInspection(inspection));
    }
}

