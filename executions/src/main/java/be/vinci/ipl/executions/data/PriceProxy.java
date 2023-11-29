package be.vinci.ipl.executions.data;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@FeignClient(name = "Ticker")
public interface PriceProxy {
    @PatchMapping("/price/{ticker}")
    void changePrice(@PathVariable String ticker, @RequestBody String p);
}
