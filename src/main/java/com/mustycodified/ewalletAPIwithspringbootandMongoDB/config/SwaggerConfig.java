package com.mustycodified.ewalletAPIwithspringbootandMongoDB.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("API").version("1.0"));
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(Arrays.asList(apiKey()));
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("ApiKeyAuth", "X-API-Key", "header");
//    }

//    private OAuth oauth() {
//        return new OAuthBuilder()
//                .name("OAuth2")
//                .grantTypes(grantTypes())
//                .scopes(scopes())
//                .build();
//    }
//
//    private List<AuthorizationScope> scopes() {
//        return Arrays.asList(
//                new AuthorizationScope("read", "Grants read access"),
//                new AuthorizationScope("write", "Grants write access"));
//    }
//
//    private List<GrantType> grantTypes() {
//        return Collections.singletonList(new AuthorizationCodeGrantBuilder()
//                .tokenEndpoint(new TokenEndpoint("https://example.com/token", "oauthtoken"))
//                .tokenRequestEndpoint(
//                        new TokenRequestEndpoint("https://example.com/auth", "clientId", "clientSecret"))
//                .build());
//    }
}
