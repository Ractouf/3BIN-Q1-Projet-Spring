package be.vinci.ipl.order;

import enums.OrderSide;
import enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false)
  private String guid;
  @Column(nullable = false)
  private String owner;
  @Column(nullable = false)
  private int timestamp;
  @Column(nullable = false)
  private String ticker;
  @Column(nullable = false)
  private int quantity;
  @Enumerated
  @Column(nullable = false)
  private OrderSide side;
  @Enumerated
  @Column(nullable = false)
  private OrderType type;
  @Column(name = "_limit")
  private double limit;
  private int filled = 0;

  public boolean invalid() {
    if (type == OrderType.LIMIT && limit <= 0)
      return false;
    return owner == null || owner.isBlank() || timestamp < 0 || ticker == null || ticker.isBlank() ||
            quantity <= 0 || side == null || type == null;
  }
}
