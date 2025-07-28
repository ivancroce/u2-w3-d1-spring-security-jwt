package ictech.u2_w3_d1_spring_security_jwt.repositories;

import ictech.u2_w3_d1_spring_security_jwt.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
}
