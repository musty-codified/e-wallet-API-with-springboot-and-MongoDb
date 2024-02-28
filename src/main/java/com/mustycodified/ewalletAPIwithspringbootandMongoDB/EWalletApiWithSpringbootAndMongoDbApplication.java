package com.mustycodified.ewalletAPIwithspringbootandMongoDB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.security.SecurityConstants;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(
		info=@Info(
				title = "eWallet RESTful Web API Documentation",
				description = "These pages document an eWallet Restful Web Service Endpoints",
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
@RequiredArgsConstructor
@Slf4j
public class EWalletApiWithSpringbootAndMongoDbApplication implements CommandLineRunner{

	@Value("${api.paystack_secret}")
	private String apiKey;

	private final MongoTemplate mongoTemplate;

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
	@Bean
	public HttpHeaders getHttpHeaders(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + apiKey);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return httpHeaders;
	}

	@Override
	public void run(String... args) throws Exception {
//		mongoTemplate.dropCollection("transactions");
//		mongoTemplate.dropCollection("wallets");
//		mongoTemplate.dropCollection("users");
	}

}
