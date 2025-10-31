package reride.reride_backend.entity;

import jakarta.persistence.*;
import reride.reride_backend.enums.VehicleAvailability;
import reride.reride_backend.enums.WebsiteVisibility;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;
    @Column(nullable = false)
    private String vehicleBrand;
    @Column(nullable = false)
    private String vehicleModel;
    @Column(nullable = false)
    private String vehicleType;
    @Column(nullable = false)
    private String vehicleModelYear;
    @Column(nullable = false)
    private String vehicleColour;
    @Column(nullable = false)
    private LocalDate vehiclePurchasedDate;
    @Column(nullable = false)
    private Long vehiclePurchasedAmount;
    @Column(nullable = false)
    private String vehicleOwnerType;
    @Column(nullable = false,unique = true)
    private String vehicleRegisterNumber;

    private String vehicleImage;
    @Column(nullable = false)
    private String vehicleInspectionBranch;
    @Column(nullable = false)
    private LocalDate vehicleInspectionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WebsiteVisibility websiteVisibility=WebsiteVisibility.NOT_VISIBLE;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "inspection_id",referencedColumnName = "inspectionId")
    private Inspection inspection;

//private String vehicleBoughtPrice;//future

    private String vehicleMileage;
    private String vehicleOutLetPrice;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleAvailability vehicleAvailability= VehicleAvailability.NOT_SOLD; //(Sold/notSold)
    private Date vehicleSoldDate;
    private String executiveName;
    private String vehicleSellingPrice;
    private String customerName;
    private String customerPhNo;
    private String documentsGiven; //(yes/no)

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDate getVehiclePurchasedDate() {
        return vehiclePurchasedDate;
    }

    public void setVehiclePurchasedDate(LocalDate vehiclePurchasedDate) {
        this.vehiclePurchasedDate = vehiclePurchasedDate;
    }

    public Long getVehiclePurchasedAmount() {
        return vehiclePurchasedAmount;
    }

    public void setVehiclePurchasedAmount(Long vehiclePurchasedAmount) {
        this.vehiclePurchasedAmount = vehiclePurchasedAmount;
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

    public LocalDate getVehicleInspectionDate() {
        return vehicleInspectionDate;
    }

    public void setVehicleInspectionDate(LocalDate vehicleInspectionDate) {
        this.vehicleInspectionDate = vehicleInspectionDate;
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

    public VehicleAvailability getVehicleAvailability() {
        return vehicleAvailability;
    }

    public void setVehicleAvailability(VehicleAvailability vehicleAvailability) {
        this.vehicleAvailability = vehicleAvailability;
    }

    public Date getVehicleSoldDate() {
        return vehicleSoldDate;
    }

    public void setVehicleSoldDate(Date vehicleSoldDate) {
        this.vehicleSoldDate = vehicleSoldDate;
    }

    public String getVehicleSellingPrice() {
        return vehicleSellingPrice;
    }

    public void setVehicleSellingPrice(String vehicleSellingPrice) {
        this.vehicleSellingPrice = vehicleSellingPrice;
    }

    public String getExecutiveName() {
        return executiveName;
    }

    public void setExecutiveName(String executiveName) {
        this.executiveName = executiveName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhNo() {
        return customerPhNo;
    }

    public void setCustomerPhNo(String customerPhNo) {
        this.customerPhNo = customerPhNo;
    }

    public String getDocumentsGiven() {
        return documentsGiven;
    }

    public void setDocumentsGiven(String documentsGiven) {
        this.documentsGiven = documentsGiven;
    }

    public WebsiteVisibility getWebsiteVisibility() {
        return websiteVisibility;
    }

    public void setWebsiteVisibility(WebsiteVisibility websiteVisibility) {
        this.websiteVisibility = websiteVisibility;
    }
}

