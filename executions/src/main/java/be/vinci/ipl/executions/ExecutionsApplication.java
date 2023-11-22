package be.vinci.ipl.executions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExecutionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutionsApplication.class, args);
	}
}
