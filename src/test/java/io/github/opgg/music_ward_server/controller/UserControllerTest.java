package io.github.opgg.music_ward_server.controller;

import io.github.opgg.music_ward_server.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseIntegrationTest {



	@Test
	@DisplayName("GET users/auth/google")
	void getGoogleLink() throws Exception {
		mockMvc.perform(get("/users/auth/google"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET users/auth/spotify")
	void getSpotifyLink() throws Exception {
		mockMvc.perform(get("/users/auth/spotify"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("POST users/auth/google")
	void getTokenByGoogleCode() throws Exception {
		mockMvc.perform(post("/users/auth/google"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("POST users/auth/google")
	void getTokenBySpotifyCodeWithJwt() throws Exception {
		mockMvc.perform(post("/users/auth/spotify"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("PUT users/auth/spotify")
	void getTokenBySpotifyCode() throws Exception {
		mockMvc.perform(put("/users/auth/spotify"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void getUserInformation() {
	}

	@Test
	void refreshToken() {
	}

	@Test
	void withdrawalUser() {
	}

	@Test
	void modifyNickname() {
	}
}