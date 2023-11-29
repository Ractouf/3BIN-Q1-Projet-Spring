package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Investor;
import be.vinci.ipl.gateway.models.InvestorWithPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "investors")
public interface InvestorsProxy {

    @GetMapping("/investors/{username}")
    Investor readOne(@PathVariable String username);

    @PostMapping("/investors/{username}")
    void createOne(@PathVariable String username, @RequestBody InvestorWithPassword investorWithPassword);

    @DeleteMapping("/investors/{username}")
    Investor deleteOne(@PathVariable String username);
}
