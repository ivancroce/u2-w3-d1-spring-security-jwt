package ictech.u2_w3_d1_spring_security_jwt.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.BadRequestException;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.NotFoundException;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewEmployeeDTO;
import ictech.u2_w3_d1_spring_security_jwt.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Cloudinary imgUploader;

    public Employee saveEmployee(NewEmployeeDTO payload) {
        // 1. Verify if the username and email are already in use.
        this.employeeRepository.findByUsername(payload.username()).ifPresent(employee -> {
            throw new BadRequestException("The username '" + payload.username() + "' is already in use.");
        });

        this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("The email '" + payload.email() + "' is already in use.");
        });

        // 2. Add server-generated values
        Employee newEmployee = new Employee(payload.username(), payload.firstName(), payload.lastName(), payload.email(), payload.password());
        newEmployee.setProfileImageUrl("https://ui-avatars.com/api/?name=" + payload.username());

        //3. Save
        Employee savedEmployee = this.employeeRepository.save(newEmployee);

        // 4. Log
        log.info("The Employee with ID '" + savedEmployee.getId() + "' was created.");

        // 5. Return the saved Employee
        return savedEmployee;
    }

    public Page<Employee> findAll(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.employeeRepository.findAll(pageable);
    }

    public Employee findById(UUID employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee findByIdAndUpdate(UUID employeeId, NewEmployeeDTO payload) {
        Employee found = this.findById(employeeId);

        if (!found.getUsername().equals(payload.username()))
            this.employeeRepository.findByUsername(payload.username()).ifPresent(employee -> {
                throw new BadRequestException("The username '" + employee.getUsername() + "' is already in use.");
            });

        if (!found.getEmail().equals(payload.email()))
            this.employeeRepository.findByEmail(payload.email()).ifPresent(employee -> {
                throw new BadRequestException("The email '" + employee.getEmail() + "' is already in use.");
            });

        found.setUsername(payload.username());
        found.setFirstName(payload.firstName());
        found.setLastName(payload.lastName());
        found.setEmail(payload.email());
        found.setPassword(payload.password());
        found.setProfileImageUrl("https://ui-avatars.com/api/?name=" + payload.username());

        Employee updatedEmpoyee = this.employeeRepository.save(found);

        log.info("The Employee with ID '" + updatedEmpoyee.getId() + "' was updated.");

        return updatedEmpoyee;
    }

    public void findByIdAndDelete(UUID employeeId) {
        Employee found = this.findById(employeeId);
        this.employeeRepository.delete(found);
    }

    public Employee uploadAvatar(MultipartFile file, UUID employeeId) {
        try {
            Employee found = this.findById(employeeId);

            Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imgUrl = (String) result.get("url");

            found.setProfileImageUrl(imgUrl);

            return employeeRepository.save(found);
        } catch (Exception e) {
            throw new BadRequestException("There were problems saving the file.");
        }
    }

    public Employee findByEmail(String email) {
        return this.employeeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("The employee with email '" + email + "' was not found."));
    }
}
