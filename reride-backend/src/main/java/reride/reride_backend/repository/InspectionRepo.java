package reride.reride_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Inspection;
import reride.reride_backend.enums.InspectionStatus;

import java.util.List;

@Repository
public interface InspectionRepo extends JpaRepository<Inspection,Long> {

    @Query("SELECT i FROM Inspection i WHERE i.inspectionStatus = :inspectionStatus")
    List<Inspection> getInspectionDetailsByInspectionsStatus(@Param("inspectionStatus") InspectionStatus inspectionStatus);

    @Query("SELECT i FROM Inspection i WHERE i.inspectionId = :inspectionId AND i.inspectionStatus = :inspectionStatus")
    Inspection getInspectionDetailsByIdandStatus(
            @Param("inspectionId") Long inspectionId,
            @Param("inspectionStatus") InspectionStatus inspectionStatus);

}
