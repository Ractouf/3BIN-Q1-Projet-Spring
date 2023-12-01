package be.vinci.ipl.investors.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient("authentication")
public interface AuthenticationProxy {
  @DeleteMapping("/authentication/{username}")
  void deleteCredentials(@PathVariable String username);
}
