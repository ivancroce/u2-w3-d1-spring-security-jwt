package ictech.u2_w3_d1_spring_security_jwt.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewTripDTO(
        @NotEmpty(message = "The destination is mandatory")
        @Size(min = 2, max = 30, message = "The destination must be between 2 and 30 characters")
        String destination,
        @NotNull(message = "The trip date is mandatory (format: YYYY-MM-DD)")
        LocalDate tripDate,
        @NotEmpty(message = "The status is mandatory (SCHEDULED or COMPLETED)")
        @Size(min = 9, max = 9, message = "The status must be of 9 characters")
        String status
) {
}
