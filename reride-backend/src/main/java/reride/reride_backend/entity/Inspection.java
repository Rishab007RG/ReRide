package reride.reride_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inspectionId;

    private String vehicleCondition;//(ok/notOk)
    private String vehicleKmsActual;
    private String vehicleKmsCorrected;
    private String vehicleCleaning;//(ok/notOk)
    private String vehicleBatteryCondition;//(ok/notOk)
    private String vehicleBatteryConditionRemarks;
    private String vehicleTyreCondition;//(ok/notOk)
    private String vehicleTyreConditionRemarks;
    private String vehicleEngineCondition;//(ok/notOk)
    private String vehicleEngineConditionRemarks;
    private String vehicleSeatCondition;//(ok/notOk)
    private String vehicleSeatConditionRemarks;
    private String vehicleFloorMatCondition;//(ok/notOk)
    private String vehicleFloorMatConditionRemarks;
    private String vehicleMirrorSet;//(ok/notOk)
    private String vehicleMirrorSetRemarks;
    private String vehiclePaintCondition;//(ok/notOk)
    private String vehiclePaintConditionRemarks;
    private String vehicleTeflonCoating;//(ok/notOk)
    private String vehicleFinalInspection;//(ok/notOk)


    @OneToOne(mappedBy = "inspection", cascade = CascadeType.ALL)
    private Vehicle vehicles;

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(String vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }

    public String getVehicleKmsActual() {
        return vehicleKmsActual;
    }

    public void setVehicleKmsActual(String vehicleKmsActual) {
        this.vehicleKmsActual = vehicleKmsActual;
    }

    public String getVehicleKmsCorrected() {
        return vehicleKmsCorrected;
    }

    public void setVehicleKmsCorrected(String vehicleKmsCorrected) {
        this.vehicleKmsCorrected = vehicleKmsCorrected;
    }

    public String getVehicleCleaning() {
        return vehicleCleaning;
    }

    public void setVehicleCleaning(String vehicleCleaning) {
        this.vehicleCleaning = vehicleCleaning;
    }

    public String getVehicleBatteryCondition() {
        return vehicleBatteryCondition;
    }

    public void setVehicleBatteryCondition(String vehicleBatteryCondition) {
        this.vehicleBatteryCondition = vehicleBatteryCondition;
    }

    public String getVehicleBatteryConditionRemarks() {
        return vehicleBatteryConditionRemarks;
    }

    public void setVehicleBatteryConditionRemarks(String vehicleBatteryConditionRemarks) {
        this.vehicleBatteryConditionRemarks = vehicleBatteryConditionRemarks;
    }

    public String getVehicleTyreCondition() {
        return vehicleTyreCondition;
    }

    public void setVehicleTyreCondition(String vehicleTyreCondition) {
        this.vehicleTyreCondition = vehicleTyreCondition;
    }

    public String getVehicleTyreConditionRemarks() {
        return vehicleTyreConditionRemarks;
    }

    public void setVehicleTyreConditionRemarks(String vehicleTyreConditionRemarks) {
        this.vehicleTyreConditionRemarks = vehicleTyreConditionRemarks;
    }

    public String getVehicleEngineCondition() {
        return vehicleEngineCondition;
    }

    public void setVehicleEngineCondition(String vehicleEngineCondition) {
        this.vehicleEngineCondition = vehicleEngineCondition;
    }

    public String getVehicleEngineConditionRemarks() {
        return vehicleEngineConditionRemarks;
    }

    public void setVehicleEngineConditionRemarks(String vehicleEngineConditionRemarks) {
        this.vehicleEngineConditionRemarks = vehicleEngineConditionRemarks;
    }

    public String getVehicleSeatCondition() {
        return vehicleSeatCondition;
    }

    public void setVehicleSeatCondition(String vehicleSeatCondition) {
        this.vehicleSeatCondition = vehicleSeatCondition;
    }

    public String getVehicleSeatConditionRemarks() {
        return vehicleSeatConditionRemarks;
    }

    public void setVehicleSeatConditionRemarks(String vehicleSeatConditionRemarks) {
        this.vehicleSeatConditionRemarks = vehicleSeatConditionRemarks;
    }

    public String getVehicleFloorMatCondition() {
        return vehicleFloorMatCondition;
    }

    public void setVehicleFloorMatCondition(String vehicleFloorMatCondition) {
        this.vehicleFloorMatCondition = vehicleFloorMatCondition;
    }

    public String getVehicleFloorMatConditionRemarks() {
        return vehicleFloorMatConditionRemarks;
    }

    public void setVehicleFloorMatConditionRemarks(String vehicleFloorMatConditionRemarks) {
        this.vehicleFloorMatConditionRemarks = vehicleFloorMatConditionRemarks;
    }

    public String getVehicleMirrorSet() {
        return vehicleMirrorSet;
    }

    public void setVehicleMirrorSet(String vehicleMirrorSet) {
        this.vehicleMirrorSet = vehicleMirrorSet;
    }

    public String getVehicleMirrorSetRemarks() {
        return vehicleMirrorSetRemarks;
    }

    public void setVehicleMirrorSetRemarks(String vehicleMirrorSetRemarks) {
        this.vehicleMirrorSetRemarks = vehicleMirrorSetRemarks;
    }

    public String getVehiclePaintCondition() {
        return vehiclePaintCondition;
    }

    public void setVehiclePaintCondition(String vehiclePaintCondition) {
        this.vehiclePaintCondition = vehiclePaintCondition;
    }

    public String getVehiclePaintConditionRemarks() {
        return vehiclePaintConditionRemarks;
    }

    public void setVehiclePaintConditionRemarks(String vehiclePaintConditionRemarks) {
        this.vehiclePaintConditionRemarks = vehiclePaintConditionRemarks;
    }

    public String getVehicleTeflonCoating() {
        return vehicleTeflonCoating;
    }

    public void setVehicleTeflonCoating(String vehicleTeflonCoating) {
        this.vehicleTeflonCoating = vehicleTeflonCoating;
    }

    public String getVehicleFinalInspection() {
        return vehicleFinalInspection;
    }

    public void setVehicleFinalInspection(String vehicleFinalInspection) {
        this.vehicleFinalInspection = vehicleFinalInspection;
    }
}
