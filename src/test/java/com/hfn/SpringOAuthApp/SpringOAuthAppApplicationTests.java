package com.hfn.SpringOAuthApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.Duration;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class SpringOAuthAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestTemplate restTemplate;

	// Test 1: Ensure unauthenticated user is redirected to OAuth2 login page
	@Test
	void testOAuth2LoginRedirect() throws Exception {
		mockMvc.perform(get("/profile")) // Access protected page
				.andExpect(status().is3xxRedirection()) // Expect redirection to login page
				.andExpect(redirectedUrl("http://localhost/oauth2/authorization/google")); // Expect exact OAuth2 login URL
	}

	// Test 2: Simulate successful OAuth2 login and access to profile page
	@Test
	void testProfilePageAccessibleWithOAuth2Login() throws Exception {
		// Create a mock OAuth2User with a simple "ROLE_USER" authority
		OAuth2User mockUser = new DefaultOAuth2User(
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
				Collections.singletonMap("name", "Mock User"), "name");

		// Create the OAuth2AuthenticationToken using the mock user, authorities, and client name
		OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(mockUser,
				mockUser.getAuthorities(), "google");

		mockMvc.perform(get("/profile")
						.with(authentication(auth))) // Simulate OAuth2 authentication
				.andExpect(status().isOk()); // Expect HTTP 200 OK for authenticated users
	}

	// Test 3: Simulate OAuth2 client request with an access token
	@Test
	void testOAuth2ClientRequest() throws Exception {
		// Create a mock OAuth2AccessToken
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
				"mock-token", Instant.now(), Instant.now().plus(Duration.ofHours(1)));

		// Create a mock OAuth2User with a simple "ROLE_USER" authority
		OAuth2User mockUser = new DefaultOAuth2User(
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
				Collections.singletonMap("name", "Mock User"), "name");

		// Create the OAuth2AuthenticationToken using the mock user, authorities, and client name
		OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(mockUser,
				mockUser.getAuthorities(), "google");

		// Mock the behavior of RestTemplate when making a request with the access token
		when(restTemplate.getForObject("https://www.googleapis.com/oauth2/v3/userinfo", String.class))
				.thenReturn("Mocked User Info");

		// Perform the GET request to the profile page, simulating an OAuth2 client call
		mockMvc.perform(get("/profile")
						.with(authentication(auth))) // Simulate OAuth2 authentication
				.andExpect(status().isOk()); // Expect a successful response
	}
}
