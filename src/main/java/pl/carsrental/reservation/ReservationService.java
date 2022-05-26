package pl.carsrental.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.carsrental.cars.Car;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
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

    public boolean isDateCorrect(Reservation reservation) {
        Date fromDate = reservation.getFromDate();
        Date toDate = reservation.getToDate();
        Date now = Date.from(Instant.now());
        int compareTo = fromDate.compareTo(toDate);
        int compareToNow = now.compareTo(fromDate);
        return compareTo < 0 && compareToNow <= 0;
    }

    public BigDecimal isBranchEndSameToStart(Reservation reservation, BigDecimal price) {
        BigDecimal extraPrice = BigDecimal.valueOf(200.00);
        if(reservation.getBranchStart() != reservation.getBranchEnd()) {
            price = price.add(extraPrice);
        }
        return price;
    }

}
