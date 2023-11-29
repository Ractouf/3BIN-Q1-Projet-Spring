package be.vinci.ipl.matching;

import data.TransactionProxy;
import models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class MatchingController {

    MatchingService service;

    TransactionProxy transactionProxy;


    public MatchingController(MatchingService service , TransactionProxy transactionProxy) {
        this.service = service;
        this.transactionProxy = transactionProxy;
    }

    @PostMapping("/trigger/{ticker}")
    public ResponseEntity<Void> findMatch(@PathVariable String ticker){
        List<Transaction> transactions = service.match(ticker);
        for (Transaction transaction : transactions) {
            transactionProxy.postOne(transaction.getTicker(), transaction.getSeller(), transaction.getBuyer(), transaction);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
