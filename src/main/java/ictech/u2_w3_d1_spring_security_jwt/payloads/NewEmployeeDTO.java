package ictech.u2_w3_d1_spring_security_jwt.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(
        @NotEmpty(message = "The username is mandatory")
        @Size(min = 2, max = 30, message = "The username must be between 2 and 30 characters")
        String username,
        @NotEmpty(message = "The first name is mandatory")
        @Size(min = 2, max = 30, message = "The first name must be between 2 and 30 characters")
        String firstName,
        @NotEmpty(message = "The last name is mandatory")
        @Size(min = 2, max = 30, message = "The last name must be between 2 and 30 characters")
        String lastName,
        @NotEmpty(message = "The email is mandatory")
        @Email(message = "The provided email is not a valid address")
        String email,
        @NotEmpty(message = "The password is mandatory")
        @Size(min = 4, message = "The password must be at least 4 characters long") // just for this exercise min = 4
        String password
) {
}
