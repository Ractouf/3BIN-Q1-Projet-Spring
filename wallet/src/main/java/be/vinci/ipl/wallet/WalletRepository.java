package be.vinci.ipl.wallet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<PositionUser, String> {
    Iterable<PositionUser> findAllByUsername(String username);
    boolean existsByUsername(String username);
}
