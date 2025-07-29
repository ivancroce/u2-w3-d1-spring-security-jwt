package ictech.u2_w3_d1_spring_security_jwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ictech.u2_w3_d1_spring_security_jwt.enums.EmployeeRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Employee implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "profile_image_url")
    private String profileImageUrl;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    public Employee(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = EmployeeRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This method must return a list of employee roles.
        // To be more precise, a collection of objects that extend GrantedAuthority.
        // SimpleGrantedAuthority is a class that represents employee roles in the Spring Security world; it extends GrantedAuthority.
        // We must pass our role (enum), converted to a string, to the object's constructor.
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    // as we are doing the login with email and psw
    @Override
    public String getUsername() {
        return this.email;
    }
}


