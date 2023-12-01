package be.vinci.ipl.gateway.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

@Repository
@FeignClient(name = "wallets")
public interface WalletsProxy {
}
