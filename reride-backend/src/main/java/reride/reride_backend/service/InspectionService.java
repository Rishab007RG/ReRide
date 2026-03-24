package reride.reride_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.enums.WebsiteVisibility;
import reride.reride_backend.repository.EmployeeRepo;
import reride.reride_backend.repository.InspectionRepo;
import reride.reride_backend.repository.VehicleRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class InspectionService {

    @Autowired
    private InspectionRepo inspectionRepo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Inspection addInspection(Inspection inspection){
        return inspectionRepo.save(inspection);
    }

    public Inspection getInspectionById(Long id) {
        return inspectionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with id: " + id));
    }

    public List<Inspection> getAllInspections() {
        return inspectionRepo.findAll();
    }

    public Inspection updateInspectionService(String authHeader, Inspection inspection, Long inspectionId) {
        String token = authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole = jwtUtil.extractUserRole(token);

        if (!employeeRole.equals("STAFF")) {
            throw new RuntimeException("Access denied: Only STAFF can perform this action.");
        }

        if (!inspectionRepo.findById(inspectionId).isPresent()) {
            throw new IllegalArgumentException("Inspection ID doesn't exist");
        }

        Inspection existingInspection = inspectionRepo.findById(inspectionId).get();

        existingInspection.setVehicleCondition(inspection.getVehicleCondition());
        existingInspection.setVehicleKmsActual(inspection.getVehicleKmsActual());
        existingInspection.setVehicleKmsCorrected(inspection.getVehicleKmsCorrected());
        existingInspection.setVehicleCleaning(inspection.getVehicleCleaning());
        existingInspection.setVehicleBatteryCondition(inspection.getVehicleBatteryCondition());
        existingInspection.setVehicleBatteryConditionRemarks(inspection.getVehicleBatteryConditionRemarks());
        existingInspection.setVehicleTyreCondition(inspection.getVehicleTyreCondition());
        existingInspection.setVehicleTyreConditionRemarks(inspection.getVehicleTyreConditionRemarks());
        existingInspection.setVehicleEngineCondition(inspection.getVehicleEngineCondition());
        existingInspection.setVehicleEngineConditionRemarks(inspection.getVehicleEngineConditionRemarks());
        existingInspection.setVehicleSeatCondition(inspection.getVehicleSeatCondition());
        existingInspection.setVehicleSeatConditionRemarks(inspection.getVehicleSeatConditionRemarks());
        existingInspection.setVehicleFloorMatCondition(inspection.getVehicleFloorMatCondition());
        existingInspection.setVehicleFloorMatConditionRemarks(inspection.getVehicleFloorMatConditionRemarks());
        existingInspection.setVehicleMirrorSet(inspection.getVehicleMirrorSet());
        existingInspection.setVehicleMirrorSetRemarks(inspection.getVehicleMirrorSetRemarks());
        existingInspection.setVehiclePaintCondition(inspection.getVehiclePaintCondition());
        existingInspection.setVehiclePaintConditionRemarks(inspection.getVehiclePaintConditionRemarks());
        existingInspection.setVehicleTeflonCoating(inspection.getVehicleTeflonCoating());
        existingInspection.setVehicleFinalInspection(inspection.getVehicleFinalInspection());
        existingInspection.setInspectionStatus(inspection.getInspectionStatus());

        return inspectionRepo.save(existingInspection);
    }

    //Update inspection status
    public Inspection updateInspectionStatus(String authHeader,Inspection inspectionForm, Long inspectionId, InspectionStatus status) {
        String token=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String role=jwtUtil.extractUserRole(token);

        Employee employee=employeeRepo.findById(employeeId)
                .orElseThrow(()->new RuntimeException("Employee doesn't exist with ID: "+employeeId));
        Inspection inspection = inspectionRepo.findById(inspectionId)
                .orElseThrow(() -> new RuntimeException("Inspection not found with ID: " + inspectionId));

        inspection.setInspectionStatus(status);
        inspection.setInspectionDate(inspectionForm.getInspectionDate());
        return inspectionRepo.save(inspection);
    }

    public List<Inspection> getInspectionDetailsByStatusService(String authHeader, String inspectionStatus) {
        String token=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);
        Employee employee=employeeRepo.findById(employeeId).orElseThrow(()->new RuntimeException("Employee doesn't exist with ID: "+employeeId));
        InspectionStatus inspectionStatusEnum=InspectionStatus.valueOf(inspectionStatus.toUpperCase());
        return inspectionRepo.getInspectionDetailsByInspectionsStatus(inspectionStatusEnum);
    }

    public Inspection getInspectionDetailsByIdService(String authHeader, String inspectionStatus, Long inspectionId) {
        String token=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);
//        if(employeeRole.equals(E))
        Employee employee=employeeRepo.findById(employeeId).orElseThrow(()->new RuntimeException("Employee doesn't exist with ID: "+employeeId));
        InspectionStatus inspectionStatusEnum=InspectionStatus.valueOf(inspectionStatus.toUpperCase());
        Inspection inspection=inspectionRepo.getInspectionDetailsByIdandStatus(inspectionId,inspectionStatusEnum);
        return inspection;
    }


//
    private static final String UPLOAD_DIR = "uploads/";

    public Vehicle updateVehicleDetails(
            Long vehicleId,
            String outletPrice,
            String mileage,
            WebsiteVisibility visibility,
            MultipartFile[] images,
            String authHeader
    ) throws JsonProcessingException {

        String token=authHeader.substring(7);
        Long employeeId=jwtUtil.extractUserId(token);
        String employeeRole=jwtUtil.extractUserRole(token);

        if (!"ADMIN".equals(employeeRole)) {
            throw new RuntimeException("Unauthorized");
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (outletPrice != null) {
            vehicle.setVehicleOutLetPrice(outletPrice);
        }

        if (mileage != null) {
            vehicle.setVehicleMileage(mileage);
        }

        if (visibility != null) {
            vehicle.setWebsiteVisibility(visibility);
        }

        if (images != null && images.length > 0) {

            List<String> imagePaths = new ArrayList<>();

            for (MultipartFile image : images) {

                try {

                    String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                    Path uploadPath = Paths.get(UPLOAD_DIR);

                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    imagePaths.add(fileName);

                } catch (IOException e) {
                    throw new RuntimeException("Image upload failed");
                }
            }

            vehicle.setVehicleImage(new ObjectMapper().writeValueAsString(imagePaths));
        }

        return vehicleRepository.save(vehicle);
    }
}

