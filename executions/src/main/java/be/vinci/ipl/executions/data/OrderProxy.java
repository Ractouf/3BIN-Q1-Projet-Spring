package be.vinci.ipl.executions.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient("order")
public interface OrderProxy {
  @PatchMapping("/order/{guid}")
  ResponseEntity<Void> changeFilled(@PathVariable String guid, @RequestBody String filled);
}