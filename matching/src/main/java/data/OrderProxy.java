package data;

import enums.OrderSide;
import java.util.List;
import models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "order")
public interface OrderProxy {
  @GetMapping("/order/open/by-ticker/{ticker}/{side}")
  List<Order> getByTicker(@PathVariable String ticker, @PathVariable OrderSide orderSide);
}
