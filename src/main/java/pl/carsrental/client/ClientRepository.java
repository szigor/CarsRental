package pl.carsrental.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> getClients();

    Client getClientById(Long clientId);

    void deleteClientById(Long clientId);
}
