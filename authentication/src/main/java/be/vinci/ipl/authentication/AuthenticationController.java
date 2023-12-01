package be.vinci.ipl.authentication;

import be.vinci.ipl.authentication.models.UnsafeCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/authentication/connect")
    public ResponseEntity<String> connect(@RequestBody UnsafeCredentials credentials) {
        if (credentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String token =  service.connect(credentials);

        if (token == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @PostMapping("/authentication/verify")
    public ResponseEntity<String> verify(@RequestBody String token) {
        String username = service.verify(token);

        if (username == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }


    @PostMapping("/authentication/{username}")
    public ResponseEntity<Void> createOne(@PathVariable String username, @RequestBody UnsafeCredentials credentials) {
        if (!Objects.equals(credentials.getUsername(), username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (credentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean created = service.createOne(credentials);

        if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/authentication/{username}")
    public ResponseEntity<Void> updateOne(@PathVariable String username, @RequestBody UnsafeCredentials credentials) {
        if (!Objects.equals(credentials.getUsername(), username)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (credentials.invalid()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean found = service.updateOne(credentials);

        if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/authentication/{username}")
    public ResponseEntity<Void> deleteCredentials(@PathVariable String username) {
        boolean found = service.deleteOne(username);

        if (!found) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

}