package ictech.u2_w3_d1_spring_security_jwt.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorRespDTO(String message, LocalDateTime timestamp, List<String> errorsList) {
}
