package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.enums.WebsiteVisibility;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    @Query("SELECT v FROM Vehicle v WHERE v.inspection.inspectionStatus = :status")
    List<Vehicle> findByInspectionStatus(@Param("status") InspectionStatus status);

    @Query("SELECT v FROM Vehicle v WHERE "
            + "(:vehicleInspectionBranch IS NULL OR v.vehicleInspectionBranch = :vehicleInspectionBranch) AND "
            + "(:vehicleBrand IS NULL OR v.vehicleBrand = :vehicleBrand) AND "
            + "(:vehicleModel IS NULL OR v.vehicleModel = :vehicleModel) AND "
            + "(:vehicleType IS NULL OR v.vehicleType = :vehicleType) AND "
            + "(:vehicleModelYear IS NULL OR v.vehicleModelYear = :vehicleModelYear) AND "
            + "(:vehicleMileage IS NULL OR v.vehicleMileage = :vehicleMileage) AND "
            + "(:vehicleOutLetPrice IS NULL OR v.vehicleOutLetPrice = :vehicleOutLetPrice)")
    List<Vehicle> searchVehicles(
            @Param("vehicleInspectionBranch") String vehicleInspectionBranch,
            @Param("vehicleBrand") String vehicleBrand,
            @Param("vehicleModel") String vehicleModel,
            @Param("vehicleType") String vehicleType,
            @Param("vehicleModelYear") String vehicleModelYear,
            @Param("vehicleMileage") String vehicleMileage,
            @Param("vehicleOutLetPrice") String vehicleOutLetPrice
    );

    List<Vehicle> findByWebsiteVisibility(WebsiteVisibility visibility);
}



