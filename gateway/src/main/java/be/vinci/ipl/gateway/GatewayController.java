package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.models.Investor;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import jakarta.ws.rs.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @GetMapping("/investor/{username}")
    public ResponseEntity<Investor> getOne(@PathVariable String username) {
        try {
            Investor investor = service.getOne(username);
            return new ResponseEntity<>(investor, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/investor/{username}")
    public ResponseEntity<Void> createOne(@PathVariable String username, @RequestBody InvestorWithPassword investorWithPassword) {
        //ToDo use createOne method gatewayService
    }
}
