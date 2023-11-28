package be.vinci.ipl.matching;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchingController {

    MatchingService service;

    public MatchingController(MatchingService service) {
        this.service = service;
    }

    @PostMapping("/trigger/{ticker}")
    public ResponseEntity<Void> findMatch(@PathVariable String ticker){

    }
}
