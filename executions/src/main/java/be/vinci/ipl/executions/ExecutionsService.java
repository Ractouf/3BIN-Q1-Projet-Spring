package be.vinci.ipl.executions;

import be.vinci.ipl.executions.data.PriceProxy;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class ExecutionsService {
    private final ExecutionsRepository repository;
    private final PriceProxy priceProxy;

    public ExecutionsService(ExecutionsRepository repository, PriceProxy priceProxy) {
        this.repository = repository;
        this.priceProxy = priceProxy;
    }

    public void updatePrice(String ticker, String price) {
        // aucune erreur possible car le prix est checké dans le Controller
        priceProxy.changePrice(ticker, price);
    }
}
