package be.vinci.ipl.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PositionUser {

    private int id;
    private String username;
    private String ticker;
    private int quantity;
    private double unitvalue;
}

