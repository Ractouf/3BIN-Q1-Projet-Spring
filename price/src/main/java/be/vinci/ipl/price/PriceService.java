package be.vinci.ipl.price;

import be.vinci.ipl.price.Models.Title;
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
      return 1;
    }
    Title tile = repository.getTitleByTicker(ticker);
    return tile.getPrice();
  }

  public boolean changePrice(String ticker, double newPrice) {
    if(newPrice <= 0) return false;

    if (!repository.existsByTicker(ticker)) {
      Title newTitle = new Title();
      newTitle.setTicker(ticker);
      newTitle.setPrice(newPrice);
      repository.save(newTitle);
    }else {
      Title updatedTitle = repository.getTitleByTicker(ticker);
      updatedTitle.setPrice(newPrice);
      repository.save(updatedTitle);
    }
    return true;

  }
}
