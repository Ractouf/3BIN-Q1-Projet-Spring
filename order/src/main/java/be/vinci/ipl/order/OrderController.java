package be.vinci.ipl.order;

import enums.OrderSide;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

  private final OrderService service;

  public OrderController (OrderService service) {
    this.service = service;
  }

  @PostMapping("/order")
  public ResponseEntity<Order> createOne(@RequestBody Order order) {
    if (order.invalid() || order.getGuid() != null)
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    service.createOne(order);

    //TODO faire appel au matching

    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @GetMapping("/order/{guid}")
  public ResponseEntity<Order> readOne(@PathVariable String guid) {
    Order order = service.readOne(guid);
    if (order == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PatchMapping("/order/{guid}")
  public ResponseEntity<Void> changeFilled(@PathVariable String guid, @RequestBody String filled) {
    Order order = service.changeFilled(guid, Integer.parseInt(filled));
    if (order == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/order/by-user/{username}")
  public ResponseEntity<Iterable<Order>> readOwner(@PathVariable String username) {
    Iterable<Order> orders = service.readOwner(username);
    if (orders == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/order/open/by-ticker/{ticker}/{side}")
  public ResponseEntity<Iterable<Order>> readTicker(@PathVariable String ticker, @PathVariable OrderSide side) {
    Iterable<Order> orders = service.readTicker(ticker,side);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
}
