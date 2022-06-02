package pl.carsrental.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.carsrental.branch.Branch;
import pl.carsrental.cars.Car;
import pl.carsrental.client.Client;
import pl.carsrental.client.ClientRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ClientRepository clientRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByPhoneNumber(String phoneNumber) {
        Integer integerPhoneNumber = Integer.valueOf(phoneNumber);
        List<Reservation> reservationList = new ArrayList<>();
        if(!isNumberExist(phoneNumber)) return reservationList;
        for (Reservation reservation : reservationRepository.findAll()) {
            if (reservation.getClient().getTel().equals(integerPhoneNumber)) {
                reservationList.add(reservation);
            }
        }
        return reservationList;
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

    public BigDecimal isCarBranchSameToStartBranch(Reservation reservation, Branch carBranch, BigDecimal price) {
        BigDecimal extraPrice = BigDecimal.valueOf(80.00);
        if (carBranch != reservation.getBranchStart()) {
            price = price.add(extraPrice);
        }
        return price;
    }

    private boolean isNumberExist(String phoneNumber) {
        int counter = 0;
        Integer integerPhoneNumber = Integer.valueOf(phoneNumber);
        for (Client client : clientRepository.findAll()) {
            if (client.getTel().equals(integerPhoneNumber)) {
                counter++;
            }
        }
        return counter != 0;
    }

}
