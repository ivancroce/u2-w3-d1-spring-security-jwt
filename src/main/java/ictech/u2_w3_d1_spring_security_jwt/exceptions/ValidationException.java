package ictech.u2_w3_d1_spring_security_jwt.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private List<String> errorMessages;

    public ValidationException(List<String> errorMessages) {
        super("Various errors of validation.");
        this.errorMessages = errorMessages;
    }
}
