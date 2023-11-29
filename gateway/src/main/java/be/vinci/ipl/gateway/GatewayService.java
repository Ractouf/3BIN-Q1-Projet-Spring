package be.vinci.ipl.gateway;

import be.vinci.ipl.gateway.data.AuthenticationProxy;
import be.vinci.ipl.gateway.data.InvestorsProxy;
import be.vinci.ipl.gateway.data.OrdersProxy;
import be.vinci.ipl.gateway.data.WalletsProxy;
import be.vinci.ipl.gateway.exceptions.BadRequestException;
import be.vinci.ipl.gateway.exceptions.ConflictException;
import be.vinci.ipl.gateway.exceptions.NotFoundException;
import be.vinci.ipl.gateway.models.Investor;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import feign.FeignException;

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
        //ToDo create wallet
    }
}
