package com.mustycodified.ewalletAPIwithspringbootandMongoDB;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info=@Info(
				title = "eWallet App RESTful Web API Documentation",
				description = "These pages document a digital Wallet Restful Web service Endpoints",
				version = "1.0",
				contact = @Contact(
						name = "Musty-codified",
						email = "ewalletappllc@gmail.com",
						url = "https://springdoc.org"
				),
				license = @License(
						name = "Apache 2.0.",
						url =  "https://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "eWallet REST API External Documentation",
				url = "https://springdoc.org"
		),
		servers = {
				@Server(
						url = "http://localhost:9090/",
						description = "The Development API Server"
				),
				// Add more servers if necessary
		}
)
@SpringBootApplication
public class EWalletApiWithSpringbootAndMongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(EWalletApiWithSpringbootAndMongoDbApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public WebClient webClient(){
//		return WebClient.builder().build();
//	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

//	@Bean
//	public SpringApplicationContext springApplicationContext(){
//		return new SpringApplicationContext();
//	}
//
//	@Bean(name = "AppProperties")
//	public AppProperties getAppProperties(){
//		return new AppProperties();
//	}
}
