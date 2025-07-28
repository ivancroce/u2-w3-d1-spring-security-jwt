package ictech.u2_w3_d1_spring_security_jwt.repositories;

import ictech.u2_w3_d1_spring_security_jwt.entities.Booking;
import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByEmployeeAndTrip_TripDate(Employee employee, LocalDate tripDate);
}
