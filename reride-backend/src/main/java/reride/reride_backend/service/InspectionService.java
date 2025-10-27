package reride.reride_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reride.reride_backend.component.JwtUtil;
import reride.reride_backend.entity.Employee;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.enums.Role;
import reride.reride_backend.repository.EmployeeRepo;
import reride.reride_backend.repository.InspectionRepo;

import java.util.List;

@Service
public class InspectionService {

    @Autowired
    InspectionRepo inspectionRepo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmployeeRepo employeeRepo;

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
}

