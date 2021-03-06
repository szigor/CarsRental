package pl.carsrental.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long clientId) {
        isClientExist(clientId);
        return clientRepository.getById(clientId);
    }

    public void save(Client client) {
        clientRepository.save(client);
        log.info("Saved " + client);
    }

    public void deleteClientById(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("client with id " + clientId + " does not exists");
        }
        clientRepository.deleteById(clientId);
    }

    private void isClientExist(Long clientId) {
        boolean exists = clientRepository.existsById(clientId);
        if (!exists) {
            throw new IllegalStateException("client with id " + clientId + " does not exists");
        }
    }
}
