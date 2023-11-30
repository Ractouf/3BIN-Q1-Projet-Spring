package be.vinci.ipl.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository repository;

  public OrderService(OrderRepository repository){
    this.repository = repository;
  }

  public boolean createOne(Order order) {
    if (repository.existsById(String.valueOf(order.getId()))) return false;

    repository.save(order);

    return true;
  }

  public boolean changeFilled(String id, int filled) {
    Order order = repository.findById(id).orElse(null);
    if(order == null)
      return false;
    order.
    return true;
  }

  public Order readOne(String id) {
    return repository.findById(id).orElse(null);
  }


}
