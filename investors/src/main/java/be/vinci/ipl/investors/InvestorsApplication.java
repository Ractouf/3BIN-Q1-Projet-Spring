package be.vinci.ipl.investors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InvestorsApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvestorsApplication.class, args);
	}
}
