package com.service.services;

import com.service.DTO.ReservationCreateDTO;
import com.service.DTO.ReservationResponseDTO;
import com.service.models.Reservation;
import com.service.models.User;
import com.service.repositories.ReservationRepository;
import com.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Autowired
    private UserRepository userRepository;

    public ReservationResponseDTO createReservationFromDTO(ReservationCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Reservation reservation = new Reservation();
        reservation.setType(dto.getType());
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        reservation.setDuration(dto.getDuration());
        reservation.setStatus(dto.getStatus());
        reservation.setUser(user);
        reservation.setItemId(dto.getItemId());

        Reservation saved = reservationRepository.save(reservation);

        ReservationResponseDTO.UserDTO userDto = new ReservationResponseDTO.UserDTO(
                saved.getUser().getId(),
                saved.getUser().getUsername()
        );

        return new ReservationResponseDTO(
                saved.getId(),
                saved.getType(),
                saved.getStartDate(),
                saved.getEndDate(),
                saved.getDuration(),
                saved.getStatus(),
                userDto,
                saved.getItemId()
        );
    }

    public List<ReservationResponseDTO> getUserReservations(User user, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return reservationRepository.findByUserWithPagination(user, pageable).stream()
                .map(reservation -> {
                    ReservationResponseDTO.UserDTO userDto = new ReservationResponseDTO.UserDTO(
                            reservation.getUser().getId(),
                            reservation.getUser().getUsername()
                    );
                    return new ReservationResponseDTO(
                            reservation.getId(),
                            reservation.getType(),
                            reservation.getStartDate(),
                            reservation.getEndDate(),
                            reservation.getDuration(),
                            reservation.getStatus(),
                            userDto,
                            reservation.getItemId()
                    );
                }).collect(Collectors.toList());
    }

    /**
     * Creates a new reservation.
     *
     * @param reservation the reservation object
     * @return the saved reservation
     */
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /**
     * Retrieves user reservations with pagination.
     *
     * @param user  the user
     * @param limit the number of reservations per page
     * @param offset the pagination offset
     * @return list of reservations
     */
    public List<Reservation> findByUserWithPagination(User user, int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return reservationRepository.findByUserWithPagination(user, pageable);
    }

    /**
     * Finds a reservation by its ID.
     *
     * @param id the reservation ID
     * @return an Optional containing the found reservation, or empty if not found
     */
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    /**
     * Retrieves all reservations.
     *
     * @return list of all reservations
     */
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Retrieves all reservations of a specific user.
     *
     * @param user the user
     * @return list of user's reservations
     */
    public List<Reservation> findByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    /**
     * Retrieves active reservations of a user.
     *
     * @param user the user
     * @return list of active reservations
     */
    public List<Reservation> findActiveReservations(User user) {
        return reservationRepository.findByUserAndStatus(user, "Active");
    }

    /**
     * Checks if a user has active reservations.
     *
     * @param user the user
     * @return true if the user has active reservations, false otherwise
     */
    public boolean hasActiveReservations(User user) {
        return reservationRepository.existsByUserAndStatus(user, "Active");
    }

    /**
     * Extends a reservation for the specified number of days.
     *
     * @param reservationId the reservation ID
     * @param extraDays the number of additional days
     * @return the updated reservation
     */
    public Optional<Reservation> extendReservation(Long reservationId, int extraDays) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            reservation.setDuration(reservation.getDuration() + extraDays);
            return reservationRepository.save(reservation);
        });
    }

    /**
     * Completes a reservation (changes status to "Completed").
     *
     * @param reservationId the reservation ID
     * @return the updated reservation
     */
    public Optional<Reservation> completeReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            reservation.setStatus("Completed");
            return reservationRepository.save(reservation);
        });
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the reservation ID
     */
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
