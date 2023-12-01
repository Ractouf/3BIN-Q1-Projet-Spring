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

  /**
   * Create a new order
   * @param order the order to create
   */
  public void createOne(Order order) {
    repository.save(order);
  }

  /**
   * Get an order from it's guid
   * @param guid the id of the order
   * @return the order or null if it doesn't exist (which allows the controller to throw a 404 error)
   */
  public Order readOne(String guid) {
    return repository.findById(guid).orElse(null);
  }

  /**
   * Update the filled value of an order
   * @param guid the id of the order
   * @param filled the amount to add
   * @return returns the order or null if it doesn't exist
   */
  public Order changeFilled(String guid, int filled) {
    Order order = repository.findById(guid).orElse(null);
    if(order != null){
      int currentFilled = order.getFilled();
      if (order.getQuantity() >= (currentFilled+filled)) {
        order.setFilled(order.getFilled() + filled);
        repository.save(order);
        return order;
      }
    }
    return order;
  }

  /**
   * Get the orders of an investor
   * @param username the investor
   * @return all the investor's orders
   */
  public Iterable<Order> readOwner(String username) {
    return repository.findByOwner(username).orElse(null);
  }

  /**
   * Get open orders of a specific ticker and side
   * @param ticker the ticker to find
   * @param side side to find
   * @return the orders or empty if none are found
   */
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
