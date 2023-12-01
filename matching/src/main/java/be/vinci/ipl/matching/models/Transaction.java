package be.vinci.ipl.matching.models;

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

  private String buyOrderGuid;

  private String sellOrderGuid;

  private int quantity;

  private double price;
}