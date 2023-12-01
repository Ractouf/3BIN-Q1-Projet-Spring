package be.vinci.ipl.investors;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient("wallets")
public interface WalletProxy {
  @GetMapping("/wallet/{username}/net-worth")
  double netWorth(@PathVariable String username);
}
