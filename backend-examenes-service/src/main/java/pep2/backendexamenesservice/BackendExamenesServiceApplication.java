package pep2.backendexamenesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackendExamenesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendExamenesServiceApplication.class, args);
	}

}
