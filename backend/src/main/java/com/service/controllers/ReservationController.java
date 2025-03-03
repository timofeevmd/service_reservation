package com.service.controllers;

import com.service.models.Reservation;
import com.service.models.User;
import com.service.security.JwtTokenProvider;
import com.service.services.ReservationService;
import com.service.services.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private final Counter createdReservations;
    private final Counter createdReservationsFailure;
    private final Counter deletedReservations;
    private final Counter deletedReservationsFailure;
    private final Timer reservationRequestTimer;
    private final Timer userReservationsRequestTimer;


    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService, JwtTokenProvider jwtTokenProvider, MeterRegistry meterRegistry) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.createdReservations = meterRegistry.counter("reservations.created.count");
        this.createdReservationsFailure = meterRegistry.counter("reservations.created.count.failure");
        this.deletedReservations = meterRegistry.counter("reservations.deleted.count");
        this.deletedReservationsFailure = meterRegistry.counter("reservations.deleted.count.failure");
        this.reservationRequestTimer = meterRegistry.timer("reservations.request.time");
        this.userReservationsRequestTimer = meterRegistry.timer("reservations.user.request.time");
    }

    /**
     * Create a new reservation.
     *
     * @param reservation Reservation object
     * @return Created reservation
     */
    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@Valid @RequestBody Reservation reservation) {
        return reservationRequestTimer.record(() -> {
            try {
                Reservation savedReservation = reservationService.createReservation(reservation);
                createdReservations.increment();
                return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
            } catch (Exception e) {
                createdReservationsFailure.increment();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating reservation: " + e.getMessage());
            }
        });
    }

    /**
     * Get a reservation by ID.
     *
     * @param id Reservation ID
     * @return Reservation
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@Valid @PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.findById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    /**
     * Get all reservations.
     *
     * @return List of all reservations
     */
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    /**
     * Get all user reservations.
     *
     * @return List of user reservations
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUserReservations(@RequestHeader("Authorization") String token, @RequestParam(defaultValue = "15") int limit, @RequestParam(defaultValue = "0") int offset) {
        return userReservationsRequestTimer.record(() -> {
            try {
                String username = jwtTokenProvider.getUsernameFromToken(token.replace("Bearer ", ""));
                Optional<User> userOpt = userService.findByUsername(username);

                if (userOpt.isEmpty()) {
                    createdReservationsFailure.increment();
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
                }

                createdReservations.increment();
                List<Reservation> reservations = reservationService.findByUserWithPagination(userOpt.get(), limit, offset);
                return ResponseEntity.ok(reservations);
            } catch (Exception e) {
                createdReservationsFailure.increment();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error loading reservations: " + e.getMessage());
            }
        });
    }

    /**
     * Delete a reservation.
     *
     * @param id Reservation ID
     * @return Operation status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@Valid @PathVariable Long id) {
        return reservationRequestTimer.record(() -> {
            Optional<Reservation> reservation = reservationService.findById(id);
            if (!reservation.isPresent()) {
                deletedReservationsFailure.increment();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation with ID \" + id + \" not found");
            }
            reservationService.deleteReservation(id);
            deletedReservations.increment();
            return ResponseEntity.ok("Reservation with ID \" + id + \" successfully deleted");
        });
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }
}
