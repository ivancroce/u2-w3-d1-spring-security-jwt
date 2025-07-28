package ictech.u2_w3_d1_spring_security_jwt.payloads;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO(
        @NotEmpty(message = "Email is mandatory")
        String email,
        @NotEmpty(message = "Password is mandatory")
        String password) {
}
