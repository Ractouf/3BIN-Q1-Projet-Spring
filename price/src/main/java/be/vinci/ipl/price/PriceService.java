package be.vinci.ipl.price;

import be.vinci.ipl.price.Models.Ticker;
import be.vinci.ipl.price.repositories.TickerRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
  private final TickerRepository repository;

  public PriceService(TickerRepository repository) {
    this.repository = repository;
  }

  public double getPrice(String ticker) {
    if (!repository.existsByTicker(ticker)) {
      Ticker newTicker = new Ticker();
      newTicker.setTicker(ticker);
      newTicker.setPrice(1);

      repository.save(newTicker);
    }
    return repository.getPriceByTicker(ticker);
  }

  public boolean changePrice(String ticker, double newPrice) {
    if(newPrice <= 0) return false;

    Ticker updatedticker = repository.getTickerByTicker(ticker);

    updatedticker.setPrice(newPrice);

    //if(repository.existsByTicker(ticker))
    repository.save(updatedticker);
    return true;

  }
}
