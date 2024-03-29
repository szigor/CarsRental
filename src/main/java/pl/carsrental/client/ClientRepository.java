package pl.carsrental.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Client c SET c.enabled = TRUE WHERE c.email = ?1")
    int enableAppUser(String email);
}
