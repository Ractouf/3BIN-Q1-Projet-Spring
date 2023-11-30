package be.vinci.ipl.matching;

import be.vinci.ipl.matching.data.OrderProxy;
import be.vinci.ipl.matching.data.PriceProxy;
import be.vinci.ipl.matching.enums.OrderSide;

import com.netflix.discovery.converters.Auto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import be.vinci.ipl.matching.models.Order;
import be.vinci.ipl.matching.enums.OrderType;
import be.vinci.ipl.matching.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static java.lang.Math.abs;

@Service
public class MatchingService {


  private final OrderProxy orderProxy;

  private final PriceProxy priceProxy;

  public MatchingService(PriceProxy priceProxy, OrderProxy orderProxy ){
    this.orderProxy = orderProxy;
    this.priceProxy = priceProxy;
  }

  public Transaction createTransaction(Order selOrder, Order buyOrder, String ticker){
    Transaction transaction = new Transaction();
    transaction.setBuyer(buyOrder.getOwner());
    transaction.setSeller(selOrder.getOwner());
    transaction.setBuy_order_guid(buyOrder.getGuid());
    transaction.setSeller(selOrder.getGuid());
    transaction.setTicker(ticker);
    return transaction;
  }

  public List<Transaction> match(String ticker){
    Iterable<Order> iterableSellOrders = orderProxy.readTicker(ticker, OrderSide.SELL);
    Iterable<Order> iterableBuyOrders = orderProxy.readTicker(ticker, OrderSide.BUY);
    Set<Order> sellOrders = new HashSet<>();
    iterableSellOrders.forEach(sellOrders::add);
    Set<Order> buyOrders = new HashSet<>();
    iterableBuyOrders.forEach(buyOrders::add);
    List<Transaction> transactions = new ArrayList<>();
    Transaction transaction = null;
    double marketPrice = priceProxy.getPriceFromTicker(ticker);
    boolean matchFinded = false;


    for (Order s : sellOrders) {
      if (s.getFilled() == s.getQuantity()) sellOrders.remove(s);
      else{
        for (Order b : buyOrders) {
          if (b.getFilled() == b.getQuantity()) buyOrders.remove(b);{
            if((s.getFilled() < s.getQuantity()) && (b.getFilled() < b.getQuantity())){
              int sLeft = s.getQuantity() - s.getFilled();
              int bLeft = b.getQuantity() - b.getFilled();
              int result = Math.min(sLeft, bLeft);
              if(s.getType().equals(OrderType.Market)){
                if(b.getType().equals(OrderType.Market)){
                  transaction = createTransaction(s, b, ticker);
                  transaction.setPrice(marketPrice);
                  matchFinded = true;
                }else if (b.getLimit() >= marketPrice){
                  transaction = createTransaction(s, b, ticker);
                  transaction.setPrice((marketPrice + b.getLimit())/2);
                  matchFinded = true;
                }
              }else{
                if(b.getType().equals(OrderType.Market) && s.getLimit() < marketPrice){
                  transaction = createTransaction(s, b, ticker);
                  transaction.setPrice((marketPrice + s.getLimit())/2);
                  matchFinded = true;
                }else if(s.getLimit() <= b.getLimit()){
                  transaction = createTransaction(s, b, ticker);
                  transaction.setPrice((marketPrice + s.getLimit())/2);
                  matchFinded = true;
                }
              }
              if(matchFinded){
                s.setFilled(s.getFilled()+result);
                b.setFilled(b.getFilled()+result);
                transactions.add(transaction);
              }
            }
          }
        }
      }
    }

    return transactions;


  }
}
