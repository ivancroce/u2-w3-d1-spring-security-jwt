package ictech.u2_w3_d1_spring_security_jwt.payloads;

import java.time.LocalDateTime;

public record ErrorRespDTO(String message, LocalDateTime timestamp) {
}
