package be.vinci.ipl.order.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
@FeignClient("matching")
public interface MatchingProxy {
    @PostMapping("/trigger/{ticker}")
    ResponseEntity<Void> findMatch(@PathVariable String ticker);
}
