package be.vinci.ipl.price.repositories;

import be.vinci.ipl.price.Models.Ticker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerRepository extends CrudRepository<Ticker, Double> {
  @Query(value= "SELECT price FROM Ticker WHERE ticker = :ticker")
  double getPriceByTicker(String ticker);

  boolean existsByTicker(String ticker);

  Ticker getTickerByTicker(String ticker);

}
