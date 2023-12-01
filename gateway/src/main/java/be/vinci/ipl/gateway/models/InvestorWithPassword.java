package be.vinci.ipl.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InvestorWithPassword {
    @JsonProperty(value = "investor_data")
    private Investor investor;
    private String password;

    public Credentials toCredentials() {
        return new Credentials(investor.getUsername(), password);
    }
}

