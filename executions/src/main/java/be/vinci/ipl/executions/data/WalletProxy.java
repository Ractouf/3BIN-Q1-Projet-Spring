package be.vinci.ipl.executions.data;

import be.vinci.ipl.executions.models.Position;
import be.vinci.ipl.executions.models.PositionUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient("wallets")
public interface WalletProxy {
  @PostMapping("/wallet/{username}")
  Iterable<PositionUser> addPosition(@PathVariable String username, @RequestBody Iterable<Position> ajouts);
}
