package be.vinci.ipl.order;

import enums.OrderSide;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {
    Optional<Iterable<Order>> findByOwner(String username);
    Iterable<Order> findByTickerAndSide(String ticker, OrderSide side);
}
