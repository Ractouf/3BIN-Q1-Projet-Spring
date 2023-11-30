package be.vinci.ipl.gateway.data;

import be.vinci.ipl.authentication.models.UnsafeCredentials;
import be.vinci.ipl.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {

    @PostMapping("/authentication/{username}")
    void createOne(@PathVariable String username, @RequestBody Credentials credentials);

    @DeleteMapping("/authentication/{username}")
    void deleteCredentials(@PathVariable String username);

    @PostMapping("/authentication/connect")
    String connect(@RequestBody UnsafeCredentials credentials);

    @PutMapping("/authentication/{username}")
    void updateOne(@PathVariable String username, @RequestBody UnsafeCredentials credentials);
}
