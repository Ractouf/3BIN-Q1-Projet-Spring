package be.vinci.ipl.matching;

import data.OrderProxy;
import data.PriceProxy;
import enums.OrderSide;
import java.util.List;
import models.Order;
import enums.OrderType;
import models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

  private final OrderProxy orderProxy;
  private final PriceProxy priceProxy;

  public MatchingService(OrderProxy orderProxy, PriceProxy priceProxy){
    this.orderProxy = orderProxy;
    this.priceProxy = priceProxy;
  }

  public Transaction match(String ticker){
    List<Order> sellOrders = orderProxy.getByTicker(ticker, OrderSide.SELL);
    List<Order> buyOrders = orderProxy.getByTicker(ticker, OrderSide.BUY);
    Transaction transaction = new Transaction();
    double marketPrice = priceProxy.getPriceFromTicker(ticker);
    boolean matchFinded = false;


    for (Order s : sellOrders) {
      for (Order b : buyOrders) {
        if((s.getFilled() < s.getQuantity()) && (b.getFilled() < b.getQuantity())){
          if(s.getType().equals(OrderType.Market)){
            if(b.getType().equals(OrderType.Market)){
              transaction.setPrice(marketPrice);
              transaction.setBuyer(b.getOwner());
              transaction.setSeller(s.getOwner());
              //TODO !!!!! enlever des listes quand quantity = filled !!!!!!!!

              (pour tous les matchs trouvés)


              matchFinded = true;
              break;
            }else if (b.getLimit() >= marketPrice){
              transaction.setPrice((marketPrice + b.getLimit())/2);
              transaction.setBuyer(b.getOwner());
              transaction.setSeller(s.getOwner());
              matchFinded = true;
              break;
            }
          }else{
            if(b.getType().equals(OrderType.Market) && s.getLimit() < marketPrice){
              transaction.setPrice((marketPrice + s.getLimit())/2);
              transaction.setBuyer(b.getOwner());
              transaction.setSeller(s.getOwner());
              matchFinded = true;
              break;
            }else if(s.getLimit() <= b.getLimit()){
              transaction.setPrice((marketPrice + s.getLimit())/2);
              transaction.setBuyer(b.getOwner());
              transaction.setSeller(s.getOwner());
              matchFinded = true;
              break;
            }
          }
        }
      }
    }

    if(matchFinded){
      return transaction;
    }
    return


  }
}
