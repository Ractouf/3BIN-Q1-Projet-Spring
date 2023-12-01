package be.vinci.ipl.executions.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position {
  private String ticker;
  private double quantity;
  private double unitvalue;
}
