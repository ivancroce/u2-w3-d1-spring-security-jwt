package ictech.u2_w3_d1_spring_security_jwt.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewBookingDTO(
        @NotNull(message = "The employee ID is mandatory")
        UUID employeeId,
        @NotNull(message = "The trip ID is mandatory")
        UUID tripId,
        @NotEmpty(message = "The notes are mandatory")
        @Size(max = 255, message = "The notes cannot exceed 255 characters")
        String notes
) {
}
