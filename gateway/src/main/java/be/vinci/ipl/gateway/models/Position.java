package be.vinci.ipl.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Position {

    public Position(String ticker, double quantity, double unitvalue) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.unitvalue = unitvalue;
    }

    private String ticker;
    private double quantity;
    private double unitvalue;
}

