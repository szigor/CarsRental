package pl.carsrental.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getReservations();

    void addReservation(Reservation reservation);

    void deleteReservation(Long reservationId);
}
