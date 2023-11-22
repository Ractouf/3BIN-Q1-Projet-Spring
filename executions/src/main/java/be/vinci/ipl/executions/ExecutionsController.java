package be.vinci.ipl.executions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionsController {
    private final ExecutionsService service;

    public ExecutionsController(ExecutionsService service) {
        this.service = service;
    }

    @PostMapping("/execute/{ticker}/{seller}/{buyer}")
    public ResponseEntity<Void> putOne(@PathVariable String ticker, @PathVariable String seller, @PathVariable String buyer, @RequestBody Transaction transaction) {
        if (transaction.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!ticker.equals(transaction.getTicker()) || !seller.equals(transaction.getSeller()) || !buyer.equals(transaction.getBuyer()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }
}
