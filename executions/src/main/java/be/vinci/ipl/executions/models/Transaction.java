package be.vinci.ipl.executions.models;

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
    @Column(name = "buy_order_guid", nullable = false)
    private String buyOrderGuid;
    @Column(name = "sell_order_guid", nullable = false)
    private String sellOrderGuid;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double price;

    public boolean invalid() {
        return ticker == null || ticker.isBlank() ||
                seller == null || seller.isBlank() ||
                buyer == null || buyer.isBlank() ||
                buyer == null || buyer.isBlank() ||
                buyOrderGuid == null || buyOrderGuid.isBlank() ||
                sellOrderGuid == null || sellOrderGuid.isBlank() ||
                quantity <= 0 || price <= 0;
    }
}
