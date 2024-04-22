package com.marcos.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.marcos.demoparkapi.dto.UserCreateDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.exceptions.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)//ANTES que metodo for executado é necessário que estaja no banco os registros
@Sql(scripts = "users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)// APOS o metodo ser executado que o delete irá ocorrer
public class UserIT {
	
	 @Autowired
	 WebTestClient webTestClient;
	 
	 @Test
	    public void createUser_usernameAndPasswordValid_returnUserCreatedWithStatus201(){
	     UserResponseDto  responseBody =  webTestClient
	                .post()
	                .uri("api/v1/usuarios")
	                .contentType(MediaType.APPLICATION_JSON)
	                .bodyValue(new UserCreateDto("anajulia@gmail.com", "123456"))
	                .exchange()
	                .expectStatus().isCreated()
	                .expectBody(UserResponseDto.class)
	                .returnResult().getResponseBody();
	    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("anajulia@gmail.com");
	    org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
	    }
	 
	 @Test
	    public void createUser_usernameInvalid_returnErrorMessageStatus422() {
	        ErrorMessage responseBody = webTestClient
	                .post()
	                .uri("/api/v1/usuarios")
	                .contentType(MediaType.APPLICATION_JSON)
	                .bodyValue(new UserCreateDto("", "123456"))
	                .exchange()
	                .expectStatus().isEqualTo(422)
	                .expectBody(ErrorMessage.class)
	                .returnResult().getResponseBody();

	        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	    }
}
