package ictech.u2_w3_d1_spring_security_jwt.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Resource with id '" + id + "' not found.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
