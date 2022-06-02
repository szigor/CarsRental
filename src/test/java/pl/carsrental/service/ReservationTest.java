package pl.carsrental.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.carsrental.branch.Branch;
import pl.carsrental.branch.BranchRepository;
import pl.carsrental.cars.*;
import pl.carsrental.client.Client;
import pl.carsrental.client.ClientRepository;
import pl.carsrental.reservation.Reservation;
import pl.carsrental.reservation.ReservationRepository;
import pl.carsrental.reservation.ReservationService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ReservationTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    BranchRepository branchRepository;

    @Test
    public void shouldSaveReservation() {
        //given
        Client client1 = clientRepository.save(Client.builder()
                .firstName("Mariusz")
                .surname("Kowalczyk")
                .tel(123456789)
                .adress("Krakow")
                .email("m.kowalczyk@gmail.com")
                .build());

        Car g63 = carRepository.save(Car.builder()
                .make("Mercedes-benz")
                .model("G63")
                .horsePower(585)
                .capacity(4.0)
                .firstRegistration(2020)
                .mileage(18.443)
                .pricePerDay(480.0)
                .bodyType(BodyType.SUV)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());

        Branch branchWwa = branchRepository.save(Branch.builder()
                .address("Warszawa")
                .car(g63)
                .build());

        Reservation reservation = reservationRepository.save(Reservation.builder()
                        .reservationDate(LocalDateTime.now())
                        .branchStart(branchWwa)
                        .branchEnd(branchWwa)
                        .carOnReservation(g63)
                .build()
        );
        //when
        List<Reservation> all = reservationRepository.findAll();
        //then
        Assertions.assertEquals(all.size(), 1);
        Assertions.assertEquals(all.get(0).getBranchStart().getAddress(), "Warszawa");
    }

    @Test
    public void isDateCorrectTestTrue() {
        //given
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservationDate(LocalDateTime.now())
                .fromDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .toDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)))
                .build()
        );
        //when
        boolean dateCorrect = reservationService.isDateCorrect(reservation);
        //then
        Assertions.assertTrue(dateCorrect);
    }

    @Test
    public void isDateCorrectTestFalse() {
        //given
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservationDate(LocalDateTime.now())
                .fromDate(Date.from(Instant.now()))
                .toDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)))
                .build()
        );
        //when
        boolean dateCorrect = reservationService.isDateCorrect(reservation);
        //then
        Assertions.assertFalse(dateCorrect);
    }

    @Test
    public void getReservationDaysTest() {
        //given
        Car g63 = carRepository.save(Car.builder()
                .make("Mercedes-benz")
                .model("G63")
                .horsePower(585)
                .capacity(4.0)
                .firstRegistration(2020)
                .mileage(18.443)
                .pricePerDay(480.0)
                .bodyType(BodyType.SUV)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservationDate(LocalDateTime.now())
                .fromDate(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .toDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)))
                .build()
        );
        //when
        BigDecimal bigDecimal = reservationService.calcBookingPrice(reservation, g63);
        //then
        Assertions.assertEquals(bigDecimal, BigDecimal.valueOf(480.0));
    }

    @Test
    public void getReservationDaysTest2() {
        //given
        Car g63 = carRepository.save(Car.builder()
                .make("Mercedes-benz")
                .model("G63")
                .horsePower(585)
                .capacity(4.0)
                .firstRegistration(2020)
                .mileage(18.443)
                .pricePerDay(480.0)
                .bodyType(BodyType.SUV)
                .fuel(Fuel.PETROL)
                .status(Status.AVAILABLE)
                .build());
        Reservation reservation = reservationRepository.save(Reservation.builder()
                .reservationDate(LocalDateTime.now())
                .fromDate(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .toDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)))
                .build()
        );
        //when
        BigDecimal bigDecimal = reservationService.calcBookingPrice(reservation, g63);
        //then
        Assertions.assertEquals(bigDecimal, BigDecimal.valueOf(1920.0));
    }
}
