package be.vinci.ipl.investors;

import be.vinci.ipl.investors.exceptions.BadRequestException;
import be.vinci.ipl.investors.exceptions.NotFoundException;
import be.vinci.ipl.investors.models.Investor;
import be.vinci.ipl.investors.models.InvestorWithPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InverstorsController {
    private final InvestorsService service;

    public InverstorsController(InvestorsService service) {
        this.service = service;
    }

    @PostMapping("/investors/{username}")
    public ResponseEntity<Void> createOne(@PathVariable String username, @RequestBody InvestorWithPassword investor) {
        if (!investor.getInvestorData().getUsername().equals(username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (investor.getInvestorData().invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean created = service.createOne(investor.getInvestorData());

        if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/investors/{username}")
    public ResponseEntity<Investor> readOne(@PathVariable String username) {
        Investor investor = service.readOne(username);

        if (investor == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(investor, HttpStatus.OK);
    }

    @PutMapping("/investors/{username}")
    public ResponseEntity<Investor> putOne(@PathVariable String username, @RequestBody Investor investor) {
        if (!investor.getUsername().equals(username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (investor.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean updated = service.updateOne(investor);

        if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(investor, HttpStatus.CREATED);
    }

    @DeleteMapping("/investors/{username}")
    public ResponseEntity<Investor> deleteOne(@PathVariable String username) {
        try {
            service.deleteOne(username);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
