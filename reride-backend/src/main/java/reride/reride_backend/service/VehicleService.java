package reride.reride_backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.entity.User;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.repository.EmployeeRepo;
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

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    JwtUtil jwtUtil;

    public Vehicle addVehicle(Vehicle vehicle){
        return  vehicleRepository.save(vehicle);
    }

    public ResponseEntity<Vehicle> addVehicleFromWebsite(
            Vehicle vehicle,
            User user,
            Inspection inspection,
            MultipartFile[] documents
    ) throws IOException {

        List<String> filePaths = new ArrayList<>();

        // Prepare upload folder
        String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return ResponseEntity.status(500).body(null);
        }

        // Handle documents
        MultipartFile[] safeDocs = (documents != null) ? documents : new MultipartFile[0];
        for (MultipartFile doc : safeDocs) {
            if (doc == null || doc.isEmpty()) continue;

            String originalFileName = doc.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) continue;

            String contentType = doc.getContentType();
            if (contentType == null ||
                    !(contentType.startsWith("image/") || contentType.equals("application/pdf"))) {
                continue;
            }

            String uniqueName = System.currentTimeMillis() + "_" + originalFileName;
            File destFile = new File(uploadDir, uniqueName);
            doc.transferTo(destFile);
            filePaths.add(uniqueName);
        }

        vehicle.setVehicleImage(objectMapper.writeValueAsString(filePaths));

        // Handle user
        if (user == null) throw new RuntimeException("User data is required");

        if (user.getUserId() != null) {
            User existingUser = userRepository.findById(user.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getUserId()));
            vehicle.setUser(existingUser);
        } else {
            User savedUser = userRepository.save(user);
            vehicle.setUser(savedUser);
        }

        // Save inspection
        Inspection savedInspection = inspectionRepo.save(inspection);
        vehicle.setInspection(savedInspection);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return ResponseEntity.ok(savedVehicle);
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

    public List<Vehicle> searchVehicles(
            String vehicleInspectionBranch,
            String vehicleBrand,
            String vehicleModel,
            String vehicleType,
            String vehicleModelYear,
            String vehicleMileage,
            String vehicleOutLetPrice
    ) {
        return vehicleRepository.searchVehicles(
                vehicleInspectionBranch,
                vehicleBrand,
                vehicleModel,
                vehicleType,
                vehicleModelYear,
                vehicleMileage,
                vehicleOutLetPrice
        );
    }

    public ResponseEntity<Vehicle> updateVehicleAndUser(
            String authHeader,
            Long vehicleId,
            Vehicle vehicleData,
            User userData,
            Inspection inspectionData,
            MultipartFile[] documents) throws IOException {

        // Extract role and ID from token
        String token = authHeader.substring(7);
        Long employeeId = jwtUtil.extractUserId(token);
        String employeeRole = jwtUtil.extractUserRole(token);

        // Fetch existing vehicle
        Vehicle existingVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + vehicleId));

        // Role-based logic
        if (employeeRole.equals("ADMIN")) {
            // Admin can update vehicle fields (except branch if restricted)
            updateVehicleFields(existingVehicle, vehicleData, false);

            // Update user fields — never create a new user
            User existingUser = existingVehicle.getUser();
            if (existingUser != null && userData != null) {
                if (userData.getUserName() != null) existingUser.setUserName(userData.getUserName());
                if (userData.getUserEmail() != null) existingUser.setUserEmail(userData.getUserEmail());
                if (userData.getUserPhoneNo() != null) existingUser.setUserPhoneNo(userData.getUserPhoneNo());
                // Role remains unchanged
                userRepository.save(existingUser);  // save updates
            }

            // Update inspection if provided
            if (inspectionData != null) {
                updateInspection(existingVehicle, inspectionData);
            }

        } else if (employeeRole.equals("STAFF")) {
            // Staff can ONLY update inspection
            if (inspectionData == null)
                throw new RuntimeException("Inspection data is required for STAFF.");
            updateInspection(existingVehicle, inspectionData);

        } else {
            throw new RuntimeException("Access denied: Only ADMIN or STAFF can perform this action.");
        }

        // Handle file uploads
        if (documents != null && documents.length > 0) {
            handleFileUploads(existingVehicle, documents);
        }

        // Save and return
        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    private void updateVehicleFields(Vehicle existing, Vehicle updated, boolean allowBranchUpdate) {
        if (updated == null) return;

        if (updated.getVehicleBrand() != null) existing.setVehicleBrand(updated.getVehicleBrand());
        if (updated.getVehicleModel() != null) existing.setVehicleModel(updated.getVehicleModel());
        if (updated.getVehicleType() != null) existing.setVehicleType(updated.getVehicleType());
        if (updated.getVehicleModelYear() != null) existing.setVehicleModelYear(updated.getVehicleModelYear());
        if (updated.getVehicleColour() != null) existing.setVehicleColour(updated.getVehicleColour());
        if (updated.getVehiclePurchasedDate() != null) existing.setVehiclePurchasedDate(updated.getVehiclePurchasedDate());
        if (updated.getVehiclePurchasedAmount() != null) existing.setVehiclePurchasedAmount(updated.getVehiclePurchasedAmount());
        if (updated.getVehicleOwnerType() != null) existing.setVehicleOwnerType(updated.getVehicleOwnerType());
        if (updated.getVehicleRegisterNumber() != null) existing.setVehicleRegisterNumber(updated.getVehicleRegisterNumber());
        if (allowBranchUpdate && updated.getVehicleInspectionBranch() != null)
            existing.setVehicleInspectionBranch(updated.getVehicleInspectionBranch());
        if (updated.getVehicleInspectionDate() != null) existing.setVehicleInspectionDate(updated.getVehicleInspectionDate());

        // Newly added fields
        if (updated.getVehicleMileage() != null) existing.setVehicleMileage(updated.getVehicleMileage());
        if (updated.getVehicleOutLetPrice() != null) existing.setVehicleOutLetPrice(updated.getVehicleOutLetPrice());
        if (updated.getVehicleAvailability() != null) existing.setVehicleAvailability(updated.getVehicleAvailability());
        if (updated.getVehicleSoldDate() != null) existing.setVehicleSoldDate(updated.getVehicleSoldDate());
        if (updated.getExecutiveName() != null) existing.setExecutiveName(updated.getExecutiveName());
        if (updated.getVehicleSellingPrice() != null) existing.setVehicleSellingPrice(updated.getVehicleSellingPrice());
        if (updated.getCustomerName() != null) existing.setCustomerName(updated.getCustomerName());
        if (updated.getCustomerPhNo() != null) existing.setCustomerPhNo(updated.getCustomerPhNo());
        if (updated.getDocumentsGiven() != null) existing.setDocumentsGiven(updated.getDocumentsGiven());

        if(updated.getWebsiteVisibility()!=null) existing.setWebsiteVisibility(updated.getWebsiteVisibility());
    }

    private void updateInspection(Vehicle vehicle, Inspection inspectionData) {
        if (inspectionData == null) return;

        Inspection existingInspection = vehicle.getInspection();
        if (existingInspection == null) {
            Inspection newIns = inspectionRepo.save(inspectionData);
            vehicle.setInspection(newIns);
            return;
        }

        // Update only non-null fields
        if (inspectionData.getVehicleCondition() != null)
            existingInspection.setVehicleCondition(inspectionData.getVehicleCondition());
        if (inspectionData.getVehicleKmsActual() != null)
            existingInspection.setVehicleKmsActual(inspectionData.getVehicleKmsActual());
        if (inspectionData.getVehicleKmsCorrected() != null)
            existingInspection.setVehicleKmsCorrected(inspectionData.getVehicleKmsCorrected());
        if (inspectionData.getVehicleCleaning() != null)
            existingInspection.setVehicleCleaning(inspectionData.getVehicleCleaning());
        if (inspectionData.getVehicleBatteryCondition() != null)
            existingInspection.setVehicleBatteryCondition(inspectionData.getVehicleBatteryCondition());
        if (inspectionData.getVehicleBatteryConditionRemarks() != null)
            existingInspection.setVehicleBatteryConditionRemarks(inspectionData.getVehicleBatteryConditionRemarks());
        if (inspectionData.getVehicleTyreCondition() != null)
            existingInspection.setVehicleTyreCondition(inspectionData.getVehicleTyreCondition());
        if (inspectionData.getVehicleTyreConditionRemarks() != null)
            existingInspection.setVehicleTyreConditionRemarks(inspectionData.getVehicleTyreConditionRemarks());
        if (inspectionData.getVehicleEngineCondition() != null)
            existingInspection.setVehicleEngineCondition(inspectionData.getVehicleEngineCondition());
        if (inspectionData.getVehicleEngineConditionRemarks() != null)
            existingInspection.setVehicleEngineConditionRemarks(inspectionData.getVehicleEngineConditionRemarks());
        if (inspectionData.getVehicleSeatCondition() != null)
            existingInspection.setVehicleSeatCondition(inspectionData.getVehicleSeatCondition());
        if (inspectionData.getVehicleSeatConditionRemarks() != null)
            existingInspection.setVehicleSeatConditionRemarks(inspectionData.getVehicleSeatConditionRemarks());
        if (inspectionData.getVehiclePaintCondition() != null)
            existingInspection.setVehiclePaintCondition(inspectionData.getVehiclePaintCondition());
        if (inspectionData.getVehiclePaintConditionRemarks() != null)
            existingInspection.setVehiclePaintConditionRemarks(inspectionData.getVehiclePaintConditionRemarks());
        if (inspectionData.getVehicleFinalInspection() != null)
            existingInspection.setVehicleFinalInspection(inspectionData.getVehicleFinalInspection());

        inspectionRepo.save(existingInspection);
    }

    private void handleFileUploads(Vehicle vehicle, MultipartFile[] documents) throws IOException {
        String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        List<String> filePaths = new ArrayList<>();
        for (MultipartFile doc : documents) {
            if (doc == null || doc.isEmpty()) continue;
            String name = System.currentTimeMillis() + "_" + doc.getOriginalFilename();
            File destFile = new File(uploadDir, name);
            doc.transferTo(destFile);
            filePaths.add(name);
        }

        if (!filePaths.isEmpty()) {
            List<String> existing = new ArrayList<>();
            try {
                if (vehicle.getVehicleImage() != null)
                    existing = objectMapper.readValue(vehicle.getVehicleImage(), List.class);
            } catch (Exception ignored) {}
            existing.addAll(filePaths);
            vehicle.setVehicleImage(objectMapper.writeValueAsString(existing));
        }
    }

    public List<Vehicle> getVehiclesByInspectionStatus(String authHeader,String inspectionStatus) {
        InspectionStatus inspectionStatusEnum=InspectionStatus.valueOf(inspectionStatus.toUpperCase());
        String token=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);
//        if(employeeRole.equals(E))
        Employee employee=employeeRepo.findById(employeeId).orElseThrow(()->new RuntimeException("Employee doesn't exist with ID: "+employeeId));
        return vehicleRepository.findByInspectionStatus(inspectionStatusEnum);
    }

}

