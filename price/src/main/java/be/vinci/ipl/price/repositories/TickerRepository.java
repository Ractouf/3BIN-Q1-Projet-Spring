package be.vinci.ipl.price.repositories;

import be.vinci.ipl.price.Models.Title;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends CrudRepository<Title, Double> {

  boolean existsByTicker(String ticker);

  Title getTitleByTicker(String ticker);

  Optional<Title> findByTicker(String ticker);

}
