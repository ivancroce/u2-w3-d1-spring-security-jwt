package ictech.u2_w3_d1_spring_security_jwt.controllers;

import ictech.u2_w3_d1_spring_security_jwt.entities.Employee;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.ValidationException;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewEmployeeDTO;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewEmployeeRespDTO;
import ictech.u2_w3_d1_spring_security_jwt.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. GET http://localhost:3001/employees
    @GetMapping
    public Page<Employee> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.employeeService.findAll(page, size, sortBy);
    }

    // 2. POST http://localhost:3001/employees (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeRespDTO createEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Employee newEmployee = this.employeeService.saveEmployee(payload);
            return new NewEmployeeRespDTO(newEmployee.getId());
        }
    }

    // 3. GET http://localhost:3001/employees/{/employeeId}
    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable UUID employeeId) {
        return this.employeeService.findById(employeeId);
    }

    // 4. PUT http://localhost:3001/employees/{/employeeId} (+ payload)
    @PutMapping("/{employeeId}")
    public Employee findEmployeeByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated NewEmployeeDTO payload) {
        return this.employeeService.findByIdAndUpdate(employeeId, payload);
    }

    // 5. DELETE http://localhost:3001/employees/{/employeeId}
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findEmployeeByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

    // 6. PATCH http://localhost:3001/employees/{employeeId}/avatar
    @PatchMapping("/{employeeId}/avatar")
    public Employee uploadImage(@RequestParam("avatar") MultipartFile file, @PathVariable UUID employeeId) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return this.employeeService.uploadAvatar(file, employeeId);
    }
}
