package be.vinci.ipl.wallet;

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
@Entity(name = "wallets")
public class PositionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String ticker;
    @Column(nullable = false)
    private double quantity;
    @Column(nullable = false)
    private double unitvalue;
}
