package reride.reride_backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.entity.User;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.repository.InspectionRepo;
import reride.reride_backend.repository.UserRepository;
import reride.reride_backend.repository.VehicleRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InspectionRepo inspectionRepo;

    public Vehicle addVehicle(Vehicle vehicle){
        return  vehicleRepository.save(vehicle);
    }

    public ResponseEntity<Vehicle> addVehicleAndUser(Vehicle vehicle, User user, Inspection inspection, MultipartFile[] documents) throws IOException {
        List<String> filePaths = new ArrayList<>();

        // Validate and prepare upload folder
        String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return ResponseEntity.status(500).body(null);
        }

        // Validate and save files
        MultipartFile[] safeDocs = (documents != null) ? documents : new MultipartFile[0];
        for (MultipartFile doc : safeDocs) {
            if (doc == null || doc.isEmpty()) continue;

            String originalFileName = doc.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) continue;

            // Validate allowed content types (optional but recommended)
            String contentType = doc.getContentType();
            if (contentType == null ||
                    !(contentType.startsWith("image/") || contentType.equals("application/pdf"))) {
                continue; // skip invalid files
            }

            // Create unique name
            String uniqueName = System.currentTimeMillis() + "_" + originalFileName;
            File destFile = new File(uploadDir, uniqueName);

            // Save file safely
            doc.transferTo(destFile);

            // Save relative path for DB
            filePaths.add(uniqueName);
        }

        // Attach file info to Vehicle
        vehicle.setVehicleImage(objectMapper.writeValueAsString(filePaths));

        // Handle user saving logic
        if (user == null) {
            throw new RuntimeException("User data is required");
        }

        if (user.getUserId() != null) {
            User existingUser = userRepository.findById(user.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getUserId()));
            vehicle.setUser(existingUser);
        } else {
            User savedUser = userRepository.save(user);
            vehicle.setUser(savedUser);
        }

        Inspection inspection1=inspectionRepo.save(inspection);
        vehicle.setInspection(inspection1);
        // Save vehicle
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }



    public List<Vehicle> getAllVehicle(){
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long vehicleId){
        return vehicleRepository.findById(vehicleId);
    }
}

