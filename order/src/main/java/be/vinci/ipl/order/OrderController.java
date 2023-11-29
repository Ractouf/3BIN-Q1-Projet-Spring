package be.vinci.ipl.order;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final OrderService service;

  public OrderController (OrderService service) {
    this.service = service;
  }

}
