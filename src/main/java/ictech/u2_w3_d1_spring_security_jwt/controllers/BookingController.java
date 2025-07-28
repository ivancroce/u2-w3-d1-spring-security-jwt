package ictech.u2_w3_d1_spring_security_jwt.controllers;

import ictech.u2_w3_d1_spring_security_jwt.entities.Booking;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.ValidationException;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewBookingDTO;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewBookingRespDTO;
import ictech.u2_w3_d1_spring_security_jwt.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    // 1. GET http://localhost:3001/bookings
    @GetMapping
    public List<Booking> getBookings() {
        return this.bookingService.findAll();
    }

    // 2. POST http://localhost:3001/bookings (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewBookingRespDTO createBooking(@RequestBody @Validated NewBookingDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Booking newBooking = this.bookingService.saveBooking(payload);
            return new NewBookingRespDTO(newBooking.getId());
        }
    }

    // 3. GET http://localhost:3001/bookings/{/bookingId}
    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable UUID bookingId) {
        return this.bookingService.findById(bookingId);
    }

    // 4. PUT http://localhost:3001/bookings/{/bookingId} (+ payload)
    @PutMapping("/{bookingId}")
    public Booking findBookingByIdAndUpdate(@PathVariable UUID bookingId, @RequestBody NewBookingDTO payload) {
        return this.bookingService.findByIdAndUpdate(bookingId, payload);
    }

    // 5. DELETE http://localhost:3001/bookings/{/bookingId}
    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findBookingByIdAndDelete(@PathVariable UUID bookingId) {
        this.bookingService.findByIdAndDelete(bookingId);
    }
}
