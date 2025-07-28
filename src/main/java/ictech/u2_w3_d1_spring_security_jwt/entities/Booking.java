package ictech.u2_w3_d1_spring_security_jwt.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Booking {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    @Column(name = "request_date")
    private LocalDate requestDate;
    private String notes;

    public Booking(Employee employee, Trip trip, String notes) {
        this.employee = employee;
        this.trip = trip;
        this.requestDate = LocalDate.now();
        this.notes = notes;
    }
}
