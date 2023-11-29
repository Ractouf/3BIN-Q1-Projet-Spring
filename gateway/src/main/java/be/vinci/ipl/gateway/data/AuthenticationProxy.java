package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {

    @PostMapping("/authentication/{pseudo}")
    void createOne(@PathVariable String username, @RequestBody Credentials credentials);
}
