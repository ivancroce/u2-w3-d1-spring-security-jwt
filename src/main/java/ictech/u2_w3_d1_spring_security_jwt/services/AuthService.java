package ictech.u2_w3_d1_spring_security_jwt.services;

import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.UnauthorizedException;
import ictech.u2_w3_d1_spring_security_jwt.payloads.LoginDTO;
import ictech.u2_w3_d1_spring_security_jwt.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateEmployeeAndGenerateToken(LoginDTO payload) {
        // find the employee in the DB
        Employee employee = this.employeeService.findByEmail(payload.email());

        // verify if the employee psw in the DB is equal to the payload psw
        if (employee.getPassword().equals(payload.password())) {
            // if the psw is the same, create and return the token
            return jwtTools.createToken(employee);
        } else {
            // if the psw is not the same, throw the UnauthorizedException
            throw new UnauthorizedException("Invalid authorization. Check your email and password.");
        }
    }
}
