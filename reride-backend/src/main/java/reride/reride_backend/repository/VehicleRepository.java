package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Vehicle;
import reride.reride_backend.enums.InspectionStatus;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    @Query("SELECT v FROM Vehicle v WHERE v.inspection.inspectionStatus = :status")
    List<Vehicle> findByInspectionStatus(@Param("status") InspectionStatus status);
}

