package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.InspectionStatus;
import reride.reride_backend.enums.VehicleAvailability;
import reride.reride_backend.enums.WebsiteVisibility;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    @Query("""
SELECT v FROM Vehicle v
WHERE v.inspection.inspectionStatus = :inspectionStatus
AND v.vehicleAvailability = :vehicleAvailability""")
    List<Vehicle> findByInspectionStatusAvailability(
            @Param("inspectionStatus") InspectionStatus inspectionStatus,
            @Param("vehicleAvailability") VehicleAvailability vehicleAvailability

    );

    @Query("""
    SELECT v FROM Vehicle v
    WHERE v.inspection.inspectionStatus = :inspectionStatus""")
    List<Vehicle> findByInspectionStatus(
            @Param("inspectionStatus") InspectionStatus inspectionStatus
    );
    @Query("""
   SELECT v FROM Vehicle v 
   WHERE (:branchId IS NULL OR v.branchId = :branchId)
   AND (:vehicleInspectionBranch IS NULL OR v.vehicleInspectionBranch = :vehicleInspectionBranch)
   AND (:vehicleBrand IS NULL OR v.vehicleBrand = :vehicleBrand)
   AND (:vehicleModel IS NULL OR v.vehicleModel = :vehicleModel)
   AND (:vehicleType IS NULL OR v.vehicleType = :vehicleType)
   AND (:vehicleModelYear IS NULL OR v.vehicleModelYear >= :vehicleModelYear)
   AND (:vehicleMileage IS NULL OR v.vehicleMileage >= :vehicleMileage)
   AND (:vehicleOutLetPrice IS NULL OR v.vehicleOutLetPrice <= :vehicleOutLetPrice)
   AND v.websiteVisibility = 'VISIBLE'
   AND v.vehicleAvailability = 'NOT_SOLD'
   """)

    List<Vehicle> searchVehicles(
            @Param("branchId") Long branchId,
            @Param("vehicleInspectionBranch") String vehicleInspectionBranch,
            @Param("vehicleBrand") String vehicleBrand,
            @Param("vehicleModel") String vehicleModel,
            @Param("vehicleType") String vehicleType,
            @Param("vehicleModelYear") String vehicleModelYear,
            @Param("vehicleMileage") String vehicleMileage,
            @Param("vehicleOutLetPrice") String vehicleOutLetPrice
    );

    @Query("SELECT v FROM Vehicle v WHERE v.websiteVisibility = :visibility AND v.vehicleAvailability = :availability")
    List<Vehicle> findByWebsiteVisibilityAndAvailability(@Param("visibility") WebsiteVisibility visibility,
                                                         @Param("availability") VehicleAvailability availability);

    @Query("SELECT v FROM Vehicle v WHERE v.vehicleId = :vehicleId AND v.websiteVisibility = :visibility AND v.vehicleAvailability = :availability")
    Optional<Vehicle> findByIdAndWebsiteVisibilityAndAvailability(@Param("vehicleId") Long vehicleId,
                                                                  @Param("visibility") WebsiteVisibility visibility,
                                                                  @Param("availability") VehicleAvailability availability);
}


