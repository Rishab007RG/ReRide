package reride.reride_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Inspection;

@Repository
public interface InspectionRepo extends JpaRepository<Inspection,Long> {
}
