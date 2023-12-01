package be.vinci.ipl.executions.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PositionUser {
  private String username;
  private String ticker;
  private double quantity;
  private double unitvalue;
}
