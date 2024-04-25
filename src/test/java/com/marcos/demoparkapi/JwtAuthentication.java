package com.marcos.demoparkapi;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.marcos.demoparkapi.dto.UserAuthDto;
import com.marcos.demoparkapi.jwt.JwtToken;

public class JwtAuthentication {

	 public  static Consumer <HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password){
	        String token = client
	                .post()
	                .uri("/api/v1/auth")
	                .bodyValue(new UserAuthDto(username, password))
	                .exchange()
	                .expectStatus().isOk()
	                .expectBody(JwtToken.class)
	                .returnResult().getResponseBody().getToken();
	        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	    }
}
