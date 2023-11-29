package be.vinci.ipl.gateway.models;

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
    private Investor investor;
    private String password;

    public Credentials toCredentials() {
        return new Credentials(investor.getUsername(), password);
    }
}

