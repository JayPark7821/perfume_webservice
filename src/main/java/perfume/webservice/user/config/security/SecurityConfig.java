package perfume.webservice.user.config.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import perfume.webservice.user.api.repository.user.UserRefreshTokenRepository;
import perfume.webservice.user.config.properties.AppProperties;
import perfume.webservice.user.config.properties.CorsProperties;
import perfume.webservice.user.oauth.entity.RoleType;
import perfume.webservice.user.oauth.exception.RestAuthenticationEntryPoint;
import perfume.webservice.user.oauth.filter.TokenAuthenticationFilter;
import perfume.webservice.user.oauth.handler.OAuth2AuthenticationFailureHandler;
import perfume.webservice.user.oauth.handler.OAuth2AuthenticationSuccessHandler;
import perfume.webservice.user.oauth.handler.TokenAccessDeniedHandler;
import perfume.webservice.user.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import perfume.webservice.user.oauth.service.CustomOAuth2UserService;
import perfume.webservice.user.oauth.service.CustomUserDetailsService;
import perfume.webservice.user.oauth.token.AuthTokenProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CorsProperties corsProperties;
	private final AppProperties appProperties;
	private final AuthTokenProvider tokenProvider;
	private final CustomUserDetailsService userDetailsService;
	private final CustomOAuth2UserService oAuth2UserService;
	private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	/*
	 * UserDetailsService 설정
	 * */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers(
				"/h2-console/**"
				, "/favicon.ico"
				, "/error"
			);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable();
		http
				.cors()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable()
				.formLogin().disable()
				.httpBasic().disable()
				.exceptionHandling()// 예외 처리 기능 작동
				.authenticationEntryPoint(
						new RestAuthenticationEntryPoint()) // authenticationEntryPoint -> (인증실패시) 처리 과정에서 예외가 발생한 경우 예외를 핸들링 하는 인터페이스
				.accessDeniedHandler(tokenAccessDeniedHandler)// accessDeniedHandler -> (인가실패시) 처리
				.and()
				.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/user/**").hasAnyAuthority(RoleType.USER.getCode(), RoleType.ADMIN.getCode())
				.antMatchers("/api/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
				.antMatchers("/").permitAll()
				.antMatchers("/swagger-ui/**").permitAll()
				.antMatchers("/swagger.html").permitAll()
				.antMatchers("/v3/**").permitAll()
				.antMatchers("/api-docs").permitAll()
				.antMatchers("/admin/**").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/profile").permitAll()
				.antMatchers("/actuator/health").permitAll()
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.authorizationEndpoint()
				.baseUri("/oauth2/authorization")
				.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
				.and()
				.redirectionEndpoint()
				.baseUri("/*/oauth2/code/*")
				.and()
				.userInfoEndpoint()
				.userService(oAuth2UserService)
				.and()
				.successHandler(oAuth2AuthenticationSuccessHandler())
				.failureHandler(oAuth2AuthenticationFailureHandler());

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	/*
	 * auth 매니저 설정
	 * */
	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	//
	//    /*
	//    * security 설정 시, 사용할 인코더 설정
	//    * */
	//    @Bean
	//    public BCryptPasswordEncoder passwordEncoder() {
	//        return new BCryptPasswordEncoder();
	//    }

	/*
	 * 토큰 필터 설정
	 * */
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(tokenProvider);
	}

	/*
	 * 쿠키 기반 인가 Repository
	 * 인가 응답을 연계 하고 검증할 때 사용.
	 * */
	@Bean
	public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
		return new OAuth2AuthorizationRequestBasedOnCookieRepository();
	}

	/*
	 * Oauth 인증 성공 핸들러
	 * */
	@Bean
	public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		System.out.println("SecurityConfig.oAuth2AuthenticationSuccessHandler");
		return new OAuth2AuthenticationSuccessHandler(
			tokenProvider,
			appProperties,
			userRefreshTokenRepository,
			oAuth2AuthorizationRequestBasedOnCookieRepository()
		);
	}

	/*
	 * Oauth 인증 실패 핸들러
	 * */
	@Bean
	public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
		return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
	}

	/*
	 * Cors 설정
	 * */
	@Bean
	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
		corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
		corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
		corsConfig.setAllowCredentials(true);
		corsConfig.setMaxAge(corsConfig.getMaxAge());

		corsConfigSource.registerCorsConfiguration("/**", corsConfig);
		return corsConfigSource;
	}
}
