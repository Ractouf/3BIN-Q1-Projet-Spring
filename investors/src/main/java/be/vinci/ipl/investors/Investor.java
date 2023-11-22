package be.vinci.ipl.investors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "investors")
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(nullable = false)
    private String username, firstname, lastname, password;
    @Column(nullable = false)
    private Date birthdate;

    public boolean invalid() {
        return username == null || username.isBlank() ||
                firstname == null || firstname.isBlank() ||
                lastname == null || lastname.isBlank() ||
                birthdate == null;
    }
}