package ictech.u2_w3_d1_spring_security_jwt.controllers;

import ictech.u2_w3_d1_spring_security_jwt.entities.Trip;
import ictech.u2_w3_d1_spring_security_jwt.exceptions.ValidationException;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewTripDTO;
import ictech.u2_w3_d1_spring_security_jwt.payloads.NewTripRespDTO;
import ictech.u2_w3_d1_spring_security_jwt.payloads.UpdateStatusDTO;
import ictech.u2_w3_d1_spring_security_jwt.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // 1. GET http://localhost:3001/trips
    @GetMapping
    public List<Trip> getTrips() {
        return this.tripService.findAll();
    }

    // 2. POST http://localhost:3001/trips (+ payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewTripRespDTO createTrip(@RequestBody @Validated NewTripDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Trip newTrip = this.tripService.saveTrip(payload);
            return new NewTripRespDTO(newTrip.getId());
        }
    }

    // 3. GET http://localhost:3001/trips/{/tripId}
    @GetMapping("/{tripId}")
    public Trip getTripById(@PathVariable UUID tripId) {
        return this.tripService.findById(tripId);
    }

    // 4. PUT http://localhost:3001/trips/{/tripId} (+ payload)
    @PutMapping("/{tripId}")
    public Trip findTripByIdAndUpdate(@PathVariable UUID tripId, @RequestBody NewTripDTO payload) {
        return this.tripService.findByIdAndUpdate(tripId, payload);
    }

    // 5. DELETE http://localhost:3001/trips/{/tripId}
    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findTripByIdAndDelete(@PathVariable UUID tripId) {
        this.tripService.findByIdAndDelete(tripId);
    }

    // 6. PATCH http://localhost:3001/trips/{/tripId}/status
    @PatchMapping("/{tripId}/status")
    public Trip updateTripStatusPatch(
            @PathVariable UUID tripId,
            @RequestBody @Validated UpdateStatusDTO payload) {
        return this.tripService.updateTripStatus(tripId, payload);
    }
}
