package ictech.u2_w3_d1_spring_security_jwt.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
