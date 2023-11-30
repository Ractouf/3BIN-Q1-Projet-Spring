package be.vinci.ipl.gateway;

import be.vinci.ipl.authentication.models.UnsafeCredentials;
import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.InvestorsProxy;
import be.vinci.ipl.gateway.data.OrdersProxy;
import be.vinci.ipl.gateway.data.WalletsProxy;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.exceptions.UnauthorizedException;
import be.vinci.ipl.gateway.models.Investor;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import be.vinci.ipl.gateway.models.Order;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class GatewayService {

    private final AuthenticationProxy authenticationProxy;
    private final InvestorsProxy investorsProxy;
    private final OrdersProxy ordersProxy;
    private final WalletsProxy walletsProxy;

    public GatewayService(AuthenticationProxy authenticationProxy,
                          InvestorsProxy investorsProxy,
                          OrdersProxy ordersProxy,
                          WalletsProxy walletsProxy) {
        this.authenticationProxy = authenticationProxy;
        this.investorsProxy = investorsProxy;
        this.ordersProxy = ordersProxy;
        this.walletsProxy = walletsProxy;
    }

    public Investor getOne(String username) throws NotFoundException {
        try {
            return investorsProxy.readOne(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    public void createOne(String username, InvestorWithPassword investorWithPassword) throws BadRequestException, ConflictException {
        try {
            investorsProxy.createOne(username, investorWithPassword);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 409) throw new ConflictException();
            else throw e;
        }

        try {
            authenticationProxy.createOne(username, investorWithPassword.toCredentials());
        } catch (FeignException e) {
            try {
                investorsProxy.deleteOne(username);
            } catch (FeignException ignored) {}

            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 409) throw new ConflictException();
            else throw e;
        }
    }

    public Investor putOne(@PathVariable String username, @RequestBody Investor investor) throws BadRequestException, NotFoundException {
        try {
            return investorsProxy.putOne(username, investor);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    public Investor deleteOne(@PathVariable String username) throws BadRequestException, NotFoundException {
        Investor investor;
        try {
            investor = investorsProxy.deleteOne(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 400) throw new BadRequestException();
            else throw e;
        }

        try {
            authenticationProxy.deleteCredentials(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }

        return investor;
    }

    public String connect(@RequestBody UnsafeCredentials credentials) throws BadRequestException, UnauthorizedException {
        try {
            return authenticationProxy.connect(credentials);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    public void updateOne(@PathVariable String username, @RequestBody UnsafeCredentials credentials) throws NotFoundException, BadRequestException {
        try {
            authenticationProxy.updateOne(username, credentials);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }

    public Order createOne(@RequestBody Order order) throws BadRequestException {
        try {
            return ordersProxy.createOne(order);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }

    public Iterable<Order> readOwner(@PathVariable String username) throws NotFoundException, UnauthorizedException {
        try {
            return ordersProxy.readOwner(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }
}
