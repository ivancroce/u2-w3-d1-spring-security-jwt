package ictech.u2_w3_d1_spring_security_jwt.tools;

import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.UnauthorizedException;
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
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            // .parse() will throw us different types of exceptions depending on whether the token has been manipulated or expired or malformed.
            throw new UnauthorizedException("Error with token. Please log in again.");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
