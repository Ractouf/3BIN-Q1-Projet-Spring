package be.vinci.ipl.wallet;

import be.vinci.ipl.wallet.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @GetMapping("/wallet/{username}/net-worth")
    public ResponseEntity<Double> netWorth(@PathVariable String username) {
        try {
            double total = service.netWorth(username);
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/wallet/{username}")
    public ResponseEntity<Iterable<PositionUser>> positions(@PathVariable String username) {
        try {
            Iterable<PositionUser> resultat = service.positions(username);
            return new ResponseEntity<>(resultat, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/wallet/{username}")
    public ResponseEntity<Iterable<PositionUser>> addPosition(@PathVariable String username, @RequestBody Iterable<Position> ajouts) {
        Iterable<PositionUser> result = service.addPosition(username,ajouts);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
