package ictech.u2_w3_d1_spring_security_jwt.controllers;

import ictech.u2_w3_d1_spring_security_jwt.payloads.LoginDTO;
import ictech.u2_w3_d1_spring_security_jwt.payloads.LoginRespDTO;
import ictech.u2_w3_d1_spring_security_jwt.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO payload) {
        String token = authService.authenticateEmployeeAndGenerateToken(payload);
        // return the token in JSON
        return new LoginRespDTO(token);
    }
}
