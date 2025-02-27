package com.service.repositories;

import com.service.models.Reservation;
import com.service.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Finds all reservations for a given user.
     *
     * @param user the user whose reservations need to be found
     * @return list of reservations
     */
    List<Reservation> findByUser(User user);

    /**
     * Finds active reservations for a specific user.
     *
     * @param user the user
     * @param status the reservation status (e.g., "Active")
     * @return list of active reservations
     */
    List<Reservation> findByUserAndStatus(User user, String status);

    /**
     * Finds reservations that start on a specific date.
     *
     * @param startDate the start date of the reservation
     * @return list of reservations
     */
    List<Reservation> findByStartDate(LocalDate startDate);

    @Query("SELECT r FROM Reservation r WHERE r.user = :user ORDER BY r.startDate DESC")
    List<Reservation> findByUserWithPagination(@Param("user") User user, Pageable pageable);

    /**
     * Checks if a user has active reservations.
     *
     * @param user the user
     * @param status the reservation status
     * @return true if active reservations exist, otherwise false
     */
    boolean existsByUserAndStatus(User user, String status);
}
