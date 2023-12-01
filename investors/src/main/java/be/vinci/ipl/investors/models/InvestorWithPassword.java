package be.vinci.ipl.investors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestorWithPassword {
    @JsonProperty(value = "investor_data")
    private Investor investorData;
    private String password;
}
