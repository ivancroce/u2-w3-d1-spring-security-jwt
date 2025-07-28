package ictech.u2_w3_d1_spring_security_jwt.repositories;

import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);
}
