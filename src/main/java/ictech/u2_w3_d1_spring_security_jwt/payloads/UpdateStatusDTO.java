package ictech.u2_w3_d1_spring_security_jwt.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UpdateStatusDTO(
        @NotEmpty(message = "The status is mandatory (SCHEDULED or COMPLETED)")
        @Size(min = 9, max = 9, message = "The status must be of 9 characters")
        String status
) {
}
