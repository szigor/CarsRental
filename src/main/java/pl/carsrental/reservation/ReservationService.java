package pl.carsrental.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.carsrental.cars.Car;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
        log.info("Added " + reservation);
    }

    public void deleteReservation(Long reservationId) {
        boolean exists = reservationRepository.existsById(reservationId);
        if (!exists) {
            throw new IllegalStateException("reservation with id " + reservationId + " does not exists");
        }
        reservationRepository.deleteById(reservationId);
    }

    public BigDecimal calcBookingPrice(Reservation reservation, Car car) {
        Double pricePerDay = car.getPricePerDay();
        Date fromDate = reservation.getFromDate();
        Date toDate = reservation.getToDate();
        long diff = toDate.getTime() - fromDate.getTime();
        long reservationDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        double price = pricePerDay * reservationDays;
        return BigDecimal.valueOf(price);
    }

}
