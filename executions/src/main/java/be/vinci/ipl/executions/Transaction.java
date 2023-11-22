package be.vinci.ipl.executions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(nullable = false)
    private String ticker;
    @Column(nullable = false)
    private String seller;
    @Column(nullable = false)
    private String buyer;
    @Column(nullable = false)
    private String buy_order_guid;
    @Column(nullable = false)
    private String sell_order_guid;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double price;

    public boolean invalid() {
        return ticker == null || ticker.isBlank() ||
                seller == null || seller.isBlank() ||
                buyer == null || buyer.isBlank() ||
                buyer == null || buyer.isBlank() ||
                buy_order_guid == null || buy_order_guid.isBlank() ||
                sell_order_guid == null || sell_order_guid.isBlank() ||
                quantity <= 0 || price <= 0;
    }
}
