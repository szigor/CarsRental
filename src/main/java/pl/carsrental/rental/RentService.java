package pl.carsrental.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentService {

    private final RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public List<Rental> getRentals() {
        return rentRepository.findAll();
    }

    public void deleteRent(Long rentId) {
        boolean exists = rentRepository.existsById(rentId);
        if (!exists) {
            throw new IllegalStateException("rent with id " + rentId + " does not exists");
        }
        rentRepository.deleteById(rentId);
    }
}
