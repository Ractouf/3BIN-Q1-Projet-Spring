package be.vinci.ipl.price;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

  PriceService service;

  public PriceController(PriceService service) {
    this.service = service;
  }

  @GetMapping("/price/{ticker}")
  public ResponseEntity<Double> getPriceFromTicker(@PathVariable String ticker){
    double price = service.getPrice(ticker);
    return new ResponseEntity<Double>(price, HttpStatus.OK);
  }

  @PatchMapping("/price/{ticker}")
  public ResponseEntity<Void> changePrice(@PathVariable String ticker, @RequestBody String p){
    double newPrice = Double.parseDouble(p);
    if(service.changePrice(ticker, newPrice)){
      return new ResponseEntity<Void>(HttpStatus.OK);
    }
    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

  }
}
