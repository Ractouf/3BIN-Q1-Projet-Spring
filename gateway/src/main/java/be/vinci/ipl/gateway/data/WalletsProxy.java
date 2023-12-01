package be.vinci.ipl.gateway.data;

import be.vinci.ipl.gateway.models.Position;
import be.vinci.ipl.gateway.models.PositionUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "wallets")
public interface WalletsProxy {

    @GetMapping("/wallet/{username}")
    Iterable<PositionUser> positions(@PathVariable String username);

    @PostMapping("/wallet/{username}")
    Iterable<PositionUser> addPosition(@PathVariable String username, @RequestBody Iterable<Position> ajouts);

    @GetMapping("/wallet/{username}/net-worth")
    Double netWorth(@PathVariable String username);
}
