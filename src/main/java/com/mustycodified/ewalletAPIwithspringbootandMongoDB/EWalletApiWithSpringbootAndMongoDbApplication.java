package com.mustycodified.ewalletAPIwithspringbootandMongoDB;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info=@Info(
				title = "eWallet Application RESTful Web API Documentation",
				description = "This pages document eWallet Restful Web service Endpoints",
				version = "1.0",
				contact = @Contact(
						name = "Musty-codified",
						email = "ilemonamustapha@gmail.com",
						url = "https://springdoc.org"
				),
				license = @License(
						name = "Apache 2.0",
						url =  "https://springdoc.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "eWallet Service Additional External Documentation",
				url = "https://springdoc.org"
		)
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
