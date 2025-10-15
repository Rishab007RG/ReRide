package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Branch;

@Repository
public interface BranchRepo extends JpaRepository<Branch,Long> {
    boolean existsByBranchGstNo(String gstNo);
    boolean existsByBranchPanNo(String panNo);
    boolean existsByBranchPhNo(String phNo);
    boolean existsByBranchEmail(String email);
}
