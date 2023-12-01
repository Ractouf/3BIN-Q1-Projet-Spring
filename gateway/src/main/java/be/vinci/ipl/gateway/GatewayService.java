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
import be.vinci.ipl.gateway.models.*;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
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

    /**
     * Retrieves an investor by username.
     *
     * @param username The username of the investor.
     * @return The retrieved investor.
     * @throws NotFoundException If the investor is not found.
     */
    public Investor getOne(String username) throws NotFoundException {
        try {
            return investorsProxy.readOne(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    /**
     * Creates a new investor.
     *
     * @param username               The username of the new investor.
     * @param investorWithPassword   The investor data including password.
     * @throws BadRequestException   If the request is malformed.
     * @throws ConflictException     If a conflict occurs (e.g., username already exists).
     */
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

    /**
     * Updates an existing investor.
     *
     * @param username The username of the investor to be updated.
     * @param investor The updated investor data.
     * @return The updated investor.
     * @throws BadRequestException If the request is malformed.
     * @throws NotFoundException  If the investor is not found.
     */
    public Investor putOne(@PathVariable String username, @RequestBody Investor investor) throws BadRequestException, NotFoundException {
        try {
            return investorsProxy.putOne(username, investor);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    /**
     * Deletes an investor by username.
     *
     * @param username The username of the investor to be deleted.
     * @return The deleted investor.
     * @throws BadRequestException If the request is malformed.
     * @throws NotFoundException  If the investor is not found.
     */
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

    /**
     * Authenticates user and returns a token.
     *
     * @param credentials The user's login credentials.
     * @return The authentication token.
     * @throws BadRequestException   If the request is malformed.
     * @throws UnauthorizedException If the user is not authorized.
     */
    public String connect(@RequestBody UnsafeCredentials credentials) throws BadRequestException, UnauthorizedException {
        try {
            return authenticationProxy.connect(credentials);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    /**
     * Updates user credentials.
     *
     * @param username    The username of the user.
     * @param credentials The new credentials.
     * @throws NotFoundException  If the user is not found.
     * @throws BadRequestException If the request is malformed.
     */
    public void updateOne(@PathVariable String username, @RequestBody UnsafeCredentials credentials) throws NotFoundException, BadRequestException {
        try {
            authenticationProxy.updateOne(username, credentials);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }

    /**
     * Creates a new order.
     *
     * @param order The order to be created.
     * @return The created order.
     * @throws BadRequestException If the request is malformed.
     */
    public Order createOne(@RequestBody Order order) throws BadRequestException {
        try {
            return ordersProxy.createOne(order);
        } catch (FeignException e) {
            if (e.status() == 400) throw new BadRequestException();
            else throw e;
        }
    }

    /**
     * Retrieves orders owned by a user.
     *
     * @param username The username of the user.
     * @return The list of orders owned by the user.
     * @throws NotFoundException  If the user is not found.
     * @throws UnauthorizedException If the user is not authorized.
     */
    public Iterable<Order> readOwner(@PathVariable String username) throws NotFoundException, UnauthorizedException {
        try {
            return ordersProxy.readOwner(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    /**
     * Retrieves positions for a user.
     *
     * @param username The username of the user.
     * @return The list of positions owned by the user.
     * @throws NotFoundException If the user is not found.
     */
    public Iterable<PositionUser> positions(@PathVariable String username) throws NotFoundException {
        try {
            return walletsProxy.positions(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    /**
     * Verifies the validity of a token.
     *
     * @param token The authentication token to be verified.
     * @return The username associated with the token.
     * @throws UnauthorizedException If the token is not valid.
     */
    public String verify(@RequestBody String token) throws UnauthorizedException {
        try {
            return authenticationProxy.verify(token);
        } catch (FeignException e) {
            if (e.status() == 401) throw new UnauthorizedException();
            else throw e;
        }
    }

    /**
     * Adds a cash position for a user.
     *
     * @param username The username of the user.
     * @param cash     The amount of cash to be added.
     * @return The updated list of positions for the user.
     */
    public Iterable<PositionUser> addPosition(@PathVariable String username, @RequestBody double cash) {
        Position position = new Position("CASH",cash, 1.0);
        List<Position> list = List.of(position);
        try {
            return walletsProxy.addPosition(username,list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the net worth of a user.
     *
     * @param username The username of the user.
     * @return The net worth of the user.
     * @throws NotFoundException If the user is not found.
     */
    public Double netWorth(@PathVariable String username) throws NotFoundException {
        try {
            return walletsProxy.netWorth(username);
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw e;
        }
    }

    /**
     * Adds a position for a specified ticker and quantity.
     *
     * @param username The username of the user.
     * @param ticker   The ticker symbol of the asset.
     * @param quantity The quantity of the asset to be added.
     * @return The updated list of positions for the user.
     */
    public Iterable<PositionUser> addTicker(@PathVariable String username,@PathVariable String ticker, @RequestBody double quantity) {
        Position position = new Position(ticker,quantity, 1.0);
        List<Position> list = List.of(position);
        try {
            return walletsProxy.addPosition(username, list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
