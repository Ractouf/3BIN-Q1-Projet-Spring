package be.vinci.ipl.matching.data;

import be.vinci.ipl.matching.enums.OrderSide;
import be.vinci.ipl.matching.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "order")
public interface OrderProxy {
  @GetMapping("/order/open/by-ticker/{ticker}/{side}")
  Iterable<Order> readTicker(@PathVariable String ticker, @PathVariable OrderSide orderSide);

}
