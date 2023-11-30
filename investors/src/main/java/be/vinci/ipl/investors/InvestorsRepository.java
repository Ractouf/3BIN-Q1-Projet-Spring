package be.vinci.ipl.investors;

import be.vinci.ipl.investors.models.Investor;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorsRepository extends CrudRepository<Investor, String> {
    boolean existsByUsername(String username);

    Optional<Investor> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);
}
