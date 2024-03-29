package be.vinci.ipl.gateway;

import be.vinci.ipl.authentication.models.UnsafeCredentials;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.exceptions.UnauthorizedException;
import be.vinci.ipl.gateway.models.Investor;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import be.vinci.ipl.gateway.models.Order;
import be.vinci.ipl.gateway.models.PositionUser;
import jakarta.ws.rs.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @GetMapping("/investors/{username}")
    public ResponseEntity<Investor> getOne(@PathVariable String username, @RequestHeader("Authorization") String token) {
        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            String rep = service.verify(token);
            if (!rep.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Investor investor = service.getOne(username);
            return new ResponseEntity<>(investor, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/investors/{username}")
    public ResponseEntity<Void> createOne(@PathVariable String username, @RequestBody InvestorWithPassword investorWithPassword) {
        if (!username.equals(investorWithPassword.getInvestor().getUsername())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            service.createOne(username, investorWithPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ConflictException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/investors/{username}")
    public ResponseEntity<Investor> putOne(@PathVariable String username, @RequestBody Investor investor) {
        try {
            return new ResponseEntity<>(service.putOne(username, investor), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/investors/{username}")
    public ResponseEntity<Investor> deleteOne(@PathVariable String username) {
        try {
            return new ResponseEntity<>(service.deleteOne(username), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/authentication/connect")
    public ResponseEntity<String> connect(@RequestBody UnsafeCredentials credentials) {
        try {
            return new ResponseEntity<>(service.connect(credentials), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/authentication/{username}")
    public ResponseEntity<Void> updateOne(@PathVariable String username, @RequestBody UnsafeCredentials credentials) {
        try {
            service.updateOne(username, credentials);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOne(@RequestBody Order order) {
        try {
            service.createOne(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/by-user/{username}")
    public ResponseEntity<Iterable<Order>> readOwner(@PathVariable String username) {
        try {
            return new ResponseEntity<>(service.readOwner(username),HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/wallet/{username}")
    public ResponseEntity<Iterable<PositionUser>> positions(@PathVariable String username, @RequestHeader("Authorization") String token) {
        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            String rep = service.verify(token);
            if (!rep.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            return new ResponseEntity<>(service.positions(username), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/wallet/{username}/cash")
    public ResponseEntity<Iterable<PositionUser>> addPosition(@PathVariable String username, @RequestBody String cash, @RequestHeader("Authorization") String token) {
        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            String rep = service.verify(token);
            if (!rep.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(service.addPosition(username, Double.parseDouble(cash)), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/wallet/{username}/net-worth")
    public ResponseEntity<Double> netWorth(@PathVariable String username,@RequestHeader("Authorization") String token) {
        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            String rep = service.verify(token);
            if (!rep.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(service.netWorth(username), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/wallet/{username}/position/{ticker}")
    public ResponseEntity<Iterable<PositionUser>> addTicker(@PathVariable String username,@PathVariable String ticker, @RequestBody String quantity, @RequestHeader("Authorization") String token) {
        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {
            String rep = service.verify(token);
            if (!rep.equals(username)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            return new ResponseEntity<>(service.addTicker(username, ticker, Double.parseDouble(quantity)), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
