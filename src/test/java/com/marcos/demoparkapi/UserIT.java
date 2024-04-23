package com.marcos.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.marcos.demoparkapi.dto.UserCreateDto;
import com.marcos.demoparkapi.dto.UserPasswordDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.exceptions.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // ANTES que metodo for
																							// executado é necessário
																							// que estaja no banco os
																							// registros
@Sql(scripts = "users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // APOS o metodo ser executado
																							// que o delete irá ocorrer
public class UserIT {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void createUser_usernameAndPasswordValid_returnUserCreatedWithStatus201() {
		UserResponseDto responseBody = webTestClient.post().uri("api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON).bodyValue(new UserCreateDto("anajulia@gmail.com", "123456"))
				.exchange().expectStatus().isCreated().expectBody(UserResponseDto.class).returnResult()
				.getResponseBody();
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("anajulia@gmail.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
	}

	@Test
	public void createUser_usernameInvalid_returnErrorMessageStatus422() {
		ErrorMessage responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("ana@gmail", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void createUsuario_ComUsernameInvalido_RetornarErrorMessageStatus422() {
		ErrorMessage responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@email", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void createUsuario_ComPasswordInvalido_RetornarErrorMessageStatus422() {
		ErrorMessage responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@email.com", "")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@email.com", "123")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("tody@email.com", "123456789")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}

	@Test
	public void createUsuario_ComUsernameRepetido_RetornarErrorMessageComStatus409() {
		ErrorMessage responseBody = webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UserCreateDto("ana@email.com", "123456")).exchange().expectStatus().isEqualTo(409)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
	}

	@Test
	public void buscarUsuario_ComIdExistente_RetornarUsuarioComStatus200() {
		UserResponseDto responseBody = webTestClient.get().uri("/api/v1/usuarios/100").exchange().expectStatus().isOk()
				.expectBody(UserResponseDto.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
	}

	@Test
	public void buscarUsuario_ComIdInexistente_RetornarErrorMessageComStatus404() {
		ErrorMessage responseBody = webTestClient.get().uri("/api/v1/usuarios/0").exchange().expectStatus().isNotFound()
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
	}

	@Test
    public void editarSenha_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
        ErrorMessage responseBody = webTestClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto("12345", "12345", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto("12345678", "12345678", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void editarSenha_ComSenhaInvalidas_RetornarErrorMessageComStatus400() {
        ErrorMessage responseBody = webTestClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto("123456", "123456", "000000"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = webTestClient
                .patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPasswordDto("000000", "123456", "123456"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

}
