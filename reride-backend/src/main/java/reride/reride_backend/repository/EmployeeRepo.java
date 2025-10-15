package reride.reride_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reride.reride_backend.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

}
