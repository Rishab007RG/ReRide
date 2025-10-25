package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmployeeEmail(String employeeEmail);

    List<Employee> findByBranch_BranchId(Long branchId);

    @Query("SELECT e FROM Employee e WHERE e.branch.branchId = :branchId AND e.employeeRole = 'ADMIN'")
    List<Employee> findByBranch_BranchIdAndRole(@Param("branchId") Long branchId);


}
