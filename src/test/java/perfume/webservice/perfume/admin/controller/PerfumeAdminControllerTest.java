package perfume.webservice.perfume.admin.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import perfume.webservice.auth.oauth.token.AuthTokenProvider;
import perfume.webservice.perfume.admin.dto.FragranceGroupSaveDto;
import perfume.webservice.perfume.admin.dto.FragranceSaveRequestDto;
import perfume.webservice.perfume.admin.dto.FragranceSaveRequestDtoList;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDto;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDtoList;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.Perfume;
import perfume.webservice.perfume.common.repository.FragranceGroupRepository;
import perfume.webservice.perfume.common.repository.FragranceRepository;
import perfume.webservice.perfume.common.repository.PerfumeRepository;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PerfumeAdminControllerTest {

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private PerfumeRepository perfumeRepository;

	@Autowired
	private FragranceRepository fragranceRepository;

	@Autowired
	private FragranceGroupRepository fragranceGroupRepository;

	@Autowired
	private EntityManager em;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mvc;

	private String adminJwtToken;
	private String userJwtToken;

	static String addPerfumeUrl = "/api/admin/perfume";
	static String addFragranceUrl = "/api/admin/fragrance";
	static String searchPerfumeUrl = "/api/admin/perfume/all/";
	static String loginUrl = "/auth/login";

	private MvcResult requestMvc(String bodyContent, String url, String token) throws Exception {

		return this.mvc.perform(post(url)
			.header("Origin", "http://localhost:3000")
			.header("Authorization", ("Bearer " + token))
			.content(bodyContent)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
		).andReturn();
	}

	@BeforeEach
	void getAccessToken() {
		DefaultMockMvcBuilder builder = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
			.dispatchOptions(true);
		this.mvc = builder.build();

		Date now = new Date();
		AuthTokenProvider authTokenProvider = new AuthTokenProvider(secret);
		this.adminJwtToken = authTokenProvider.createAuthToken("test", "ROLE_ADMIN", new Date(now.getTime() + 1800000))
			.getToken();
		this.userJwtToken = authTokenProvider.createAuthToken("test2", "ROLE_USER", new Date(now.getTime() + 1800000))
			.getToken();

	}

	@AfterEach
	void clearDB() {
		perfumeRepository.deleteAll();
		fragranceRepository.deleteAll();
	}

	@Test
	void 향수_향없이_등록_ADMIN_테스트() throws Exception {
		//given
		PerfumeSaveRequestDto perfumeA = PerfumeSaveRequestDto.builder()
			.name("perfumeA")
			.description("perfumeA desc")
			.build();

		PerfumeSaveRequestDto perfumeB = PerfumeSaveRequestDto.builder()
			.name("perfumeB")
			.description("perfumeB desc")
			.build();

		PerfumeSaveRequestDtoList requestBody1 = PerfumeSaveRequestDtoList.builder()
			.perfumeSaveList(List.of(perfumeA, perfumeB))
			.build();
		//=============================== 다건 등록 테스트 ========================

		PerfumeSaveRequestDto perfumeC = PerfumeSaveRequestDto.builder()
			.name("perfumeC")
			.description("perfumeC desc")
			.build();

		PerfumeSaveRequestDtoList requestBody2 = PerfumeSaveRequestDtoList.builder()
			.perfumeSaveList(List.of(perfumeC))
			.build();
		//=============================== 단건 등록 테스트 ========================
		//when
		requestMvc(objectMapper.writeValueAsString(requestBody1), addPerfumeUrl, adminJwtToken);
		requestMvc(objectMapper.writeValueAsString(requestBody2), addPerfumeUrl, adminJwtToken);

		//then
		List<Perfume> all = perfumeRepository.findAll();
		assertThat(all).extracting("name").containsExactly("perfumeA", "perfumeB", "perfumeC");

	}

	@Test
	void 향수_향없이_등록_USER_테스트() throws Exception {
		//given
		PerfumeSaveRequestDto perfumeA = PerfumeSaveRequestDto.builder()
			.name("perfumeA")
			.description("perfumeA desc")
			.build();

		PerfumeSaveRequestDto perfumeB = PerfumeSaveRequestDto.builder()
			.name("perfumeB")
			.description("perfumeB desc")
			.build();

		PerfumeSaveRequestDtoList requestBody = PerfumeSaveRequestDtoList.builder()
			.perfumeSaveList(List.of(perfumeA, perfumeB))
			.build();

		//when
		MvcResult mvcResult = requestMvc(objectMapper.writeValueAsString(requestBody), addPerfumeUrl, userJwtToken);

		//then
		assertThat(mvcResult.getResponse().getContentAsString()).contains("403", "Access is denied");
	}

	@Test
	void 파라미터오류_향수_등록_테스트() throws Exception {
		//given
		PerfumeSaveRequestDto perfumeA = PerfumeSaveRequestDto.builder()
			.description("perfumeA desc")
			.build();

		PerfumeSaveRequestDto perfumeB = PerfumeSaveRequestDto.builder()
			.name("perfumeB")
			.build();

		PerfumeSaveRequestDtoList requestBody = PerfumeSaveRequestDtoList.builder()
			.perfumeSaveList(List.of(perfumeA, perfumeB))
			.build();

		String requestBodyString = "{\"perfumeSaveList\" : [ {\"name\":\"test향수\"}]}";

		//when
		MvcResult mvcResult1 = requestMvc("{}", addPerfumeUrl, adminJwtToken);
		MvcResult mvcResult2 = requestMvc(objectMapper.writeValueAsString(requestBody), addPerfumeUrl, adminJwtToken);
		MvcResult mvcResult3 = requestMvc(requestBodyString, addPerfumeUrl, adminJwtToken);

		//then
		assertThat(mvcResult1.getResponse().getContentAsString()).contains("400");
		assertThat(mvcResult2.getResponse().getContentAsString()).contains("400");
		assertThat(mvcResult3.getResponse().getContentAsString()).contains("400");

	}

	@Test
	void 향등록_ADMIN_테스트() throws Exception {
		//given
		FragranceSaveRequestDto fragA = FragranceSaveRequestDto.builder()
			.name("fresh")
			.desc("so fresh")
			.build();

		FragranceSaveRequestDto fragB = FragranceSaveRequestDto.builder()
			.name("woody")
			.desc("so woody")
			.build();

		FragranceSaveRequestDtoList requestBody1 = FragranceSaveRequestDtoList.builder()
			.fragranceSaveList(List.of(fragA, fragB))
			.build();
		//=============================== 다건 등록 테스트 ========================

		FragranceSaveRequestDto fragC = FragranceSaveRequestDto.builder()
			.name("deep")
			.desc("so deep")
			.build();

		FragranceSaveRequestDtoList requestBody2 = FragranceSaveRequestDtoList.builder()
			.fragranceSaveList(List.of(fragC))
			.build();
		//=============================== 단건 등록 테스트 ========================
		//when
		requestMvc(objectMapper.writeValueAsString(requestBody1), addFragranceUrl, adminJwtToken);
		requestMvc(objectMapper.writeValueAsString(requestBody2), addFragranceUrl, adminJwtToken);

		//then
		List<Fragrance> all = fragranceRepository.findAll();
		assertThat(all).extracting("name").containsExactly("fresh", "woody", "deep");
		assertThat(all).extracting("description").containsExactly("so fresh", "so woody", "so deep");
	}

	@Test
	void 향수_향_등록_메핑_테스트() throws Exception {
		//given
		List<Long> fragIds = saveFragrance(5);
		List<FragranceGroupSaveDto> fragGroupListA = getFragGroupList(fragIds.subList(0, 2), 50);
		List<FragranceGroupSaveDto> fragGroupListB = getFragGroupList(fragIds, 20);

		PerfumeSaveRequestDto perfumeA = PerfumeSaveRequestDto.builder()
			.name("perfumeA")
			.description("perfumeA desc")
			.fragranceList(fragGroupListA)
			.build();

		PerfumeSaveRequestDto perfumeB = PerfumeSaveRequestDto.builder()
			.name("perfumeB")
			.description("perfumeB desc")
			.fragranceList(fragGroupListB)
			.build();

		List<PerfumeSaveRequestDto> perfumeSaveList = List.of(perfumeA, perfumeB);

		PerfumeSaveRequestDtoList requestBody = PerfumeSaveRequestDtoList.builder()
			.perfumeSaveList(perfumeSaveList)
			.build();

		//when
		MvcResult mvcResult = requestMvc(objectMapper.writeValueAsString(requestBody), addPerfumeUrl, adminJwtToken);

		//then
		for (PerfumeSaveRequestDto perfumeSaveRequestDto : perfumeSaveList) {
			Perfume perfume = perfumeRepository.findByName(perfumeSaveRequestDto.getName())
				.orElseThrow(() -> new Exception());
			List<Long> savedFragIds = perfume.getFragranceGroup()
				.stream()
				.map(f -> f.getFragrance().getId())
				.collect(Collectors.toList());
			List<Long> toSaveFragIds = perfumeSaveRequestDto.getFragrance()
				.stream()
				.map(FragranceGroupSaveDto::getId)
				.collect(Collectors.toList());

			assertThat(perfume.getFragranceGroup().get(0).getContainPercentage()).isEqualTo(
				perfumeSaveRequestDto.getFragrance().get(0).getPercentage());
			assertThat(perfume.getDescription()).isEqualTo(perfumeSaveRequestDto.getDescription());
			assertThat(savedFragIds).hasSameElementsAs(toSaveFragIds);
		}
		assertThat(mvcResult.getResponse().getContentAsString()).contains("200", "SUCCESS");
	}

	private List<FragranceGroupSaveDto> getFragGroupList(List<Long> fragIds, int percentage) {
		return fragIds.stream().map(i -> FragranceGroupSaveDto.builder()
			.id(i)
			.percentage(percentage)
			.build()).collect(Collectors.toList());
	}

	private List<Long> saveFragrance(int size) {
		List<Long> ids = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Fragrance savedFrag = fragranceRepository.save(Fragrance.builder()
				.name("테스트향" + i)
				.description("테스트향" + i)
				.build());
			ids.add(savedFrag.getId());
		}
		em.flush();
		em.clear();
		return ids;
	}
}