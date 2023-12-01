package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "order")
public interface OrdersProxy {
    @PostMapping("/order")
    Order createOne(@RequestBody Order order);

    @GetMapping("/order/by-user/{username}")
    Iterable<Order> readOwner(@PathVariable String username);
}
