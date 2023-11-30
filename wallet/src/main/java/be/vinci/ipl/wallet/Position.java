package be.vinci.ipl.wallet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Position {
    private String ticker;
    private int quantity;
    private double unitvalue;
}
