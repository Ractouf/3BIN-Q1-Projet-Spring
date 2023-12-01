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
public class Investor {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Date birthdate;

}

