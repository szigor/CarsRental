package pl.carsrental.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.carsrental.client.Client;
import pl.carsrental.client.ClientRepository;
import pl.carsrental.client.ClientService;

import java.util.List;

@SpringBootTest
public class ClientTest {

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    public void shouldSaveClient() {
        //given
        Client client = Client.builder()
                .firstName("Pawel")
                .surname("Budny")
                .email("p.budny@wp.pl")
                .adress("Frombork")
                .tel(698556247)
                .build();
        clientRepository.save(client);
        //when
        List<Client> clientList = clientRepository.findAll();
        //then
        Assertions.assertEquals(clientList.size(), 1);
        Assertions.assertEquals(clientList.get(0).getFirstName(), "Pawel");
        Assertions.assertEquals(clientList.get(0).getSurname(), "Budny");
        Assertions.assertEquals(clientList.get(0).getEmail(), "p.budny@wp.pl");
        Assertions.assertEquals(clientList.get(0).getAdress(), "Frombork");
        Assertions.assertEquals(clientList.get(0).getTel(), 698556247);
    }

    @Test
    public void shouldGetClientById() {
        //given
        Client client = Client.builder()
                .id(1L)
                .firstName("Pawel")
                .surname("Budny")
                .email("p.budny@wp.pl")
                .adress("Frombork")
                .tel(698556247)
                .build();
        clientRepository.save(client);
        Client client2 = Client.builder()
                .id(2L)
                .firstName("Maria")
                .surname("Budny")
                .email("m.budny@wp.pl")
                .adress("Frombork")
                .tel(698556257)
                .build();
        clientRepository.save(client);
        //when
        Client clientWithId2 = clientRepository.getById(2L);
        //then
        Assertions.assertEquals(clientWithId2.getId(), client2.getId());
    }
}
