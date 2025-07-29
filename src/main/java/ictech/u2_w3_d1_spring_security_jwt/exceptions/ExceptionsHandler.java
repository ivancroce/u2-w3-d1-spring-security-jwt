package ictech.u2_w3_d1_spring_security_jwt.exceptions;

import ictech.u2_w3_d1_spring_security_jwt.payloads.ErrorRespDTO;
import ictech.u2_w3_d1_spring_security_jwt.payloads.ValidationErrorRespDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ValidationErrorRespDTO handleValidationErrors(ValidationException ex) {
        return new ValidationErrorRespDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrorMessages());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorRespDTO handleBadRequest(BadRequestException ex) {
        return new ErrorRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorRespDTO handleUnauthorizedErrors(UnauthorizedException ex) {
        return new ErrorRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorRespDTO handleForbidden(AuthorizationDeniedException ex) {
        return new ErrorRespDTO("You do not have permission to access.", LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorRespDTO handleNotFound(NotFoundException ex) {
        return new ErrorRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorRespDTO handleServerError(Exception ex) {
        ex.printStackTrace();
        return new ErrorRespDTO("There was a generic error! We promise we'll fix it soon!", LocalDateTime.now());
    }
}
