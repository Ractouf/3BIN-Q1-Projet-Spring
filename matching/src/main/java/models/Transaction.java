package models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Transaction {

  private String ticker;

  private String seller;

  private String buyer;

  private String buy_order_guid;

  private String sell_order_guid;

  private int quantity;

  private double price;
}