package be.vinci.ipl.order;

import enums.OrderSide;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

  private final OrderRepository repository;

  public OrderService(OrderRepository repository){
    this.repository = repository;
  }

  public void createOne(Order order) {
    repository.save(order);
  }

  public Order readOne(String guid) {
    return repository.findById(guid).orElse(null);
  }

  public boolean changeFilled(String guid, int filled) {
    Order order = repository.findById(guid).orElse(null);
    if(order != null){
      int currentFilled = order.getFilled();
      if (order.getQuantity() >= (currentFilled+filled)) {
        order.setFilled(order.getFilled() + filled);
        return true;
      }
    }
    return false;
  }

  public Iterable<Order> readOwner(String username) {
    return repository.findByOwner(username).orElse(null);
  }

  public Iterable<Order> readTicker(String ticker, OrderSide side){
    Iterable<Order> orders = repository.findByTickerAndSide(ticker, side);
    ArrayList<Order> results = new ArrayList<>();
    for (Order order : orders) {
      if (order.getQuantity() > order.getFilled()){
        results.add(order);
      }
    }
    return results;
  }



}
