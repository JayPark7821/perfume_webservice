package perfume.webservice.user.api.controller.auth;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import perfume.webservice.user.api.entity.auth.AuthReqModel;
import perfume.webservice.user.api.entity.user.User;
import perfume.webservice.user.api.repository.user.UserRepository;
import perfume.webservice.user.oauth.entity.ProviderType;
import perfume.webservice.user.oauth.entity.RoleType;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class AuthControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public MockMvc mvc;

	static String loginUrl = "/api/auth/login";
	static String homeUrl = "/";

	private String getLoginForm(String id, String password) throws JsonProcessingException {
		AuthReqModel authReqModel = new AuthReqModel(id, password);
		return objectMapper.writeValueAsString(authReqModel);
	}


	private MvcResult requestMvc(String bodyContent, String url) throws Exception {
		return this.mvc.perform(post(url)
			.header("Origin", "http://localhost:3000")
			.content(bodyContent)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andReturn();
	}

	private Map<String, String> getTokensFromMvcResult(MvcResult result) throws
		JsonProcessingException,
		UnsupportedEncodingException {

		Map<String, Object> resultBody = objectMapper.readValue(result.getResponse().getContentAsString(),
			new TypeReference<Map<String, Object>>() {
			});
		Map<String, String> body = (Map<String, String>)resultBody.get("data");

		Optional<Cookie> refresh_token = Arrays.stream(result.getResponse().getCookies())
			.filter(c -> c.getName().equals("refresh_token")).findAny();

		String refreshToken = refresh_token.get().getValue();
		String token = body.get("token");

		return Map.of("token", token, "refreshToken", refreshToken);
	}

	@BeforeEach
	void setup() {
		User testId = userRepository.findByUserId("testId");

		userRepository.save(new User("testId", "testUser", "test@test.com", "Y", "~~~",
				ProviderType.valueOf("LOCAL"), RoleType.USER, LocalDateTime.now(), LocalDateTime.now(),
				passwordEncoder.encode("test")));

		DefaultMockMvcBuilder builder = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.dispatchOptions(true);
		this.mvc = builder.build();
	}

	@Test
	void cors_테스트() throws Exception {

		this.mvc.perform(post(loginUrl)
				.header("Origin", "http://www.naver.com"))
			.andExpect(status().isForbidden());

		this.mvc.perform(post(loginUrl)
				.header("Origin", "http://localhost:3002"))
			.andExpect(status().isForbidden());
	}

	@Test
	void form_로그인_실패_테스트() throws Exception {

		//given
		String bodyContent = getLoginForm("te22222t.com", "22222");

		//when
		MvcResult result = requestMvc(bodyContent, loginUrl);

		//then
		assertThat(result.getResponse().getContentAsString()).contains("Bad credentials");
		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void form_로그인_성공_테스트() throws Exception {
		//given
		String bodyContent = getLoginForm("test@test.com", "test");

		//when
		MvcResult result = requestMvc(bodyContent, loginUrl);
		System.out.println("result = " + result.getResponse().getContentAsString());
		//then
		assertThat(result.getResponse().getContentAsString()).contains("SUCCESS");
		assertThat(result.getResponse().getContentAsString()).contains("token");

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	void form_로그인시_토큰생성_테스트() throws Exception {
		//given
		String bodyContent = getLoginForm("test@test.com", "test");

		//when
		MvcResult result = requestMvc(bodyContent, loginUrl);

		//then
		Map<String, Object> resultBody = objectMapper.readValue(result.getResponse().getContentAsString(),
			new TypeReference<Map<String, Object>>() {
			});
		Map<String, String> body = (Map<String, String>)resultBody.get("data");
		Optional<Cookie> refresh_token = Arrays.stream(result.getResponse().getCookies())
			.filter(c -> c.getName().equals("refresh_token")).findAny();

		assertThat(refresh_token).isNotEmpty();
		assertThat(body.get("token")).isNotEmpty();
	}

	@Test
	void 재로그인시_토큰재발급_테스트() throws Exception {
		//given
		String bodyContent = getLoginForm("test@test.com", "test");
		//when
		MvcResult result = requestMvc(bodyContent, loginUrl);
		Map<String, String> firstTokens = getTokensFromMvcResult(result);
		Thread.sleep(1000); // 이유는 ???....... 캐싱을하나?......
		MvcResult result2 = requestMvc(bodyContent, loginUrl);
		Map<String, String> secondTokens = getTokensFromMvcResult(result2);

		//then
		assertThat(firstTokens.get("token")).isNotEqualTo(secondTokens.get("token"));
		assertThat(firstTokens.get("refreshToken")).isNotEqualTo(secondTokens.get("refreshToken"));
	}

	//    @Test
	//    void 토큰으로_home_접근_가능여부_테스트 () throws Exception {
	//        //given
	//        String bodyContent = getLoginForm("test@test.com", "test");
	//        //when
	//        MvcResult result = requestMvc(bodyContent, loginUrl);
	//        System.out.println("result.getResponse().getContentAsString() = " + result.getResponse().getContentAsString());
	//        Map<String, String> firstTokens = getTokensFromMvcResult(result);
	//        System.out.println("firstTokens = " + firstTokens);
	//        MvcResult mvcResult = this.mvc.perform(get(homeUrl)
	//                .header("Origin", "http://localhost:3000")
	//                .header("Authorization", "Bearer " + firstTokens.get("token"))
	//        ).andReturn();
	//        //then
	//        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("ok");
	//    }

}