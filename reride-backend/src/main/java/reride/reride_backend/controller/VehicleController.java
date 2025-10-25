package reride.reride_backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.entity.User;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.repository.VehicleRepository;
import reride.reride_backend.service.UserService;
import reride.reride_backend.service.VehicleService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("vehicle")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501"})

public class VehicleController {


    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping(value = "/addVehicle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addVehicleWithImg(
            @RequestPart("vehicle") String vehicleJson,
            @RequestPart("user") String userJson,
            @RequestPart("inspection") String inspectionJson,

            @RequestPart(value = "documents", required = false) MultipartFile[] documents
    ) throws IOException {
        System.out.println("here in controller");
        Vehicle vehicle = objectMapper.readValue(vehicleJson, Vehicle.class);
        User user = objectMapper.readValue(userJson, User.class);
        Inspection inspection = objectMapper.readValue(inspectionJson, Inspection.class);

        vehicleService.addVehicleAndUser(vehicle,user,inspection, documents);
        return ResponseEntity.ok("details added successfully");
    }


    @GetMapping("/getVehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicle(){
        return ResponseEntity.ok(vehicleService.getAllVehicle());
    }

    @GetMapping("/getVehicle/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId){
        return vehicleService.getVehicleById(vehicleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Admin can update vehicle(including after inspection(selling price..)) details
    @PutMapping(value = "/updateVehicle/{vehicleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateVehicleWithImg(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long vehicleId,
            @RequestPart("vehicle") String vehicleJson,
            @RequestPart("user") String userJson,
            @RequestPart("inspection") String inspectionJson,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents
    ) throws IOException {

        System.out.println("Inside VehicleController → updateVehicleWithImg()");

        // Convert incoming JSON strings to Java objects
        Vehicle vehicle = objectMapper.readValue(vehicleJson, Vehicle.class);
        User user = objectMapper.readValue(userJson, User.class);
        Inspection inspection = objectMapper.readValue(inspectionJson, Inspection.class);

        // Call service method (role-based logic happens inside)
        vehicleService.updateVehicleAndUser(authHeader, vehicleId, vehicle, user, inspection, documents);

        return ResponseEntity.ok("Vehicle details updated successfully");
    }

}

