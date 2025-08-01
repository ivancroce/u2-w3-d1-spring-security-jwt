package ictech.u2_w3_d1_spring_security_jwt.security;

import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import ictech.u2_w3_d1_spring_security_jwt.services.EmployeeService;
import ictech.u2_w3_d1_spring_security_jwt.tools.JWTTools;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // This is the method that will be called for every request that needs to verify the token (so apart from /login and /register)
        // This filter will be responsible for retrieving the token, verifying its validity, and if everything is OK, forwarding the request
        // to the next filter; if there are problems, reporting an error.
        // One of the interesting features of filters is that they have access to all parts of the request, including the headers.
        // The token will be placed in the headers (Authorization header).

        // ================================================================ AUTHENTICATION ================================================================

        // 1. Get the "Authorization" Header from the request and check if it's well-formed ("Bearer 34j1k2lkjxcljxkjclkj..."), if it's not there or if it's not in the right format --> 401
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnavailableException("Please enter the token in the Authorization Header in the correct format.");
        // 2. Extract the token from the header
        String token = authHeader.replace("Bearer ", "");
        // 3. We check if the token is ok: we check if it has been manipulated (via signature), or if it's expired (via Expiration Date) verifyToken(token)
        jwtTools.verifyToken(token);

        // ================================================================ AUTHORIZATIOON ================================================================

        // 1. Search for the employee in the DB by id (the id is in the token)
        String employeeId = jwtTools.extractIdFromToken(token);
        Employee currentEmployee = this.employeeService.findById(UUID.fromString(employeeId));
        // 2. Once I've found the employee, I need to associate it with the Security Context.
        // This is how Spring Security knows which employee is making the request.
        // This is essential because when we get to the controller, we need to know the role of the person making the request.
        // Or, in some cases, we're just interested in knowing who the employee is, to check, for example, whether they're the owner of the resource they're reading/modifying/deleting.
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, currentEmployee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication); // update the SecurityContext by associating the authenticated employee (with his roles)

        // 3. If everything is OK, we pass the request to the next one (which can be either a filter or the controller directly)
        filterChain.doFilter(request, response);
    }

    // We disable this filter for certain endpoints like /auth/login and /auth/register
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Ignore the filter for all requests to http://localhost:3001/auth/...
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
