package data;


import models.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "executions")
public interface TransactionProxy {
  @PostMapping("/execute/{ticker}/{seller}/{buyer}")
  void postOne(@PathVariable String ticker, @PathVariable String seller, @PathVariable String buyer, @RequestBody Transaction transaction);
}