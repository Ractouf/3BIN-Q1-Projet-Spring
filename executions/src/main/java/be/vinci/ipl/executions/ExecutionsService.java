package be.vinci.ipl.executions;

import be.vinci.ipl.executions.data.OrderProxy;
import be.vinci.ipl.executions.data.PriceProxy;
import be.vinci.ipl.executions.data.WalletProxy;
import be.vinci.ipl.executions.models.Position;
import be.vinci.ipl.executions.models.Transaction;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class ExecutionsService {
    private final PriceProxy priceProxy;
    private final OrderProxy orderProxy;
    private final WalletProxy walletProxy;

    public ExecutionsService(PriceProxy priceProxy, OrderProxy orderProxy, WalletProxy walletProxy) {
        this.priceProxy = priceProxy;
        this.orderProxy = orderProxy;
        this.walletProxy = walletProxy;
    }

    /**
     * Update the price of a ticker
     * @param ticker the ticker
     * @param price the new price
     */
    public void updatePrice(String ticker, String price) {
      priceProxy.changePrice(ticker, price);
    }

    /**
     * Update the orders quantity
     * @param transaction the transaction
     */
    public void updateOrders(Transaction transaction) {
      orderProxy.changeFilled(transaction.getBuyOrderGuid(), String.valueOf(transaction.getQuantity()));
      orderProxy.changeFilled(transaction.getSellOrderGuid(), String.valueOf(transaction.getQuantity()));
    }

    /**
     * Update the cash of the seller and the buyer
     * @param transaction the transaction
     */
    public void updateCash(Transaction transaction) {
      double amountCash = transaction.getPrice() * transaction.getQuantity();
      ArrayList<Position> list = new ArrayList<>();
      list.add(new Position("CASH", -amountCash, 1));
      walletProxy.addPosition(transaction.getBuyer(), list);

      list.clear();
      list.add(new Position("CASH", amountCash, 1));
      walletProxy.addPosition(transaction.getSeller(), list);
    }
}
