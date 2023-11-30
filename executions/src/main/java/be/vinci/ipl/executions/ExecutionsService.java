package be.vinci.ipl.executions;

import be.vinci.ipl.executions.data.OrderProxy;
import be.vinci.ipl.executions.data.PriceProxy;
import be.vinci.ipl.executions.models.Transaction;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class ExecutionsService {
    private final PriceProxy priceProxy;
    private final OrderProxy orderProxy;

    public ExecutionsService(PriceProxy priceProxy, OrderProxy orderProxy) {
        this.priceProxy = priceProxy;
        this.orderProxy = orderProxy;
    }

    public void updatePrice(String ticker, String price) {
      priceProxy.changePrice(ticker, price);
    }

    public void updateOrders(Transaction transaction) {
      orderProxy.changeFilled(transaction.getBuyOrderGuid(), String.valueOf(transaction.getQuantity()));
      orderProxy.changeFilled(transaction.getSellOrderGuid(), String.valueOf(transaction.getQuantity()));
    }
}
