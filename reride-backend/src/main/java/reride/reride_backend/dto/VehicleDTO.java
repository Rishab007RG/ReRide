package reride.reride_backend.dto;

public class VehicleDTO {

    private Long vehicleId;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleType;
    private String vehicleModelYear;
    private String vehicleColour;
    private String vehicleOwnerType;
    private String vehicleRegisterNumber;
    private String vehicleImage;
    private String vehicleInspectionBranch;
    private String vehicleMileage;
    private String vehicleOutLetPrice;
    private Long branchId;

    public VehicleDTO(Long vehicleId, String vehicleBrand, String vehicleModel, String vehicleType,
                      String vehicleModelYear, String vehicleColour, String vehicleOwnerType,
                      String vehicleRegisterNumber, String vehicleImage,
                      String vehicleInspectionBranch, String vehicleMileage, String vehicleOutLetPrice,Long branchId) {
        this.vehicleId = vehicleId;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehicleModelYear = vehicleModelYear;
        this.vehicleColour = vehicleColour;
        this.vehicleOwnerType = vehicleOwnerType;
        this.vehicleRegisterNumber = vehicleRegisterNumber;
        this.vehicleImage = vehicleImage;
        this.vehicleInspectionBranch = vehicleInspectionBranch;
        this.vehicleMileage = vehicleMileage;
        this.vehicleOutLetPrice = vehicleOutLetPrice;
        this.branchId=branchId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleModelYear() {
        return vehicleModelYear;
    }

    public void setVehicleModelYear(String vehicleModelYear) {
        this.vehicleModelYear = vehicleModelYear;
    }

    public String getVehicleColour() {
        return vehicleColour;
    }

    public void setVehicleColour(String vehicleColour) {
        this.vehicleColour = vehicleColour;
    }

    public String getVehicleOwnerType() {
        return vehicleOwnerType;
    }

    public void setVehicleOwnerType(String vehicleOwnerType) {
        this.vehicleOwnerType = vehicleOwnerType;
    }

    public String getVehicleRegisterNumber() {
        return vehicleRegisterNumber;
    }

    public void setVehicleRegisterNumber(String vehicleRegisterNumber) {
        this.vehicleRegisterNumber = vehicleRegisterNumber;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getVehicleInspectionBranch() {
        return vehicleInspectionBranch;
    }

    public void setVehicleInspectionBranch(String vehicleInspectionBranch) {
        this.vehicleInspectionBranch = vehicleInspectionBranch;
    }

    public String getVehicleMileage() {
        return vehicleMileage;
    }

    public void setVehicleMileage(String vehicleMileage) {
        this.vehicleMileage = vehicleMileage;
    }

    public String getVehicleOutLetPrice() {
        return vehicleOutLetPrice;
    }

    public void setVehicleOutLetPrice(String vehicleOutLetPrice) {
        this.vehicleOutLetPrice = vehicleOutLetPrice;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
