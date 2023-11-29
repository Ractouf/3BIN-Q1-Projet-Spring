package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import enums.OrderSide;
import enums.OrderType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  private String owner;
  private int timestamp;
  private String ticker;
  private int quantity;
  @Enumerated
  @JsonFormat(shape = Shape.STRING)
  private OrderSide side;
  @Enumerated
  @JsonFormat(shape = Shape.STRING)
  private OrderType type;
  private double limit;
  private int filled;
}
