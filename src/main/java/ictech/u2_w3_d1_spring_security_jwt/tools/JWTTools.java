package ictech.u2_w3_d1_spring_security_jwt.tools;

import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    // The Jwts class (coming from jjwt-api) has mainly 2 methods: builder() and parser() which we will use to create and verify tokens respectively
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Employee employee) {
        return Jwts.builder()
                .subject(String.valueOf(employee.getId())) // the employee has the token
                .issuedAt(new Date(System.currentTimeMillis())) // Token issuance date in milliseconds - IAT - Issued At
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Token Expiration Date, also in milliseconds
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact(); // compact everything in a string
    }

    public void verifyToken(String token) {
    }
}
