package perfume.webservice.user.api.controller.auth;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import perfume.webservice.user.api.entity.auth.AuthReqModel;
import perfume.webservice.user.api.entity.user.UserRefreshToken;
import perfume.webservice.user.api.repository.user.UserRefreshTokenRepository;
import perfume.webservice.user.config.properties.AppProperties;
import perfume.webservice.user.oauth.entity.RoleType;
import perfume.webservice.user.oauth.entity.UserPrincipal;
import perfume.webservice.user.oauth.token.AuthToken;
import perfume.webservice.user.oauth.token.AuthTokenProvider;
import perfume.webservice.user.utils.CookieUtil;
import perfume.webservice.user.utils.HeaderUtil;
import perfume.webservice.common.dto.ApiResponses;
import perfume.webservice.common.dto.TokenDto;
import perfume.webservice.common.exception.CustomAuthTokenException;
import perfume.webservice.common.exception.ResponseMsgType;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Transactional
@Tag(name = "AuthController", description = "인증/토큰 발급")
public class AuthController {

	private final AppProperties appProperties;
	private final AuthTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	private final static long THREE_DAYS_MSEC = 259200000;
	private final static String REFRESH_TOKEN = "refresh_token";

	@Operation(summary = "ID&PW 로그인 처리", description = "ID&PW 로그인 처리 <br> <br> 로그인 성공시 response body에 access token을 쿠키에 refresh token을 담아서 return <br> <br> 이후 권한이 필요한 요청에서는 header - Authorization 속성에 'Bearer ' + access token 을 담아 요청")
	@PostMapping("/login")
	public ApiResponses<TokenDto> login(HttpServletRequest request, HttpServletResponse response,
		@RequestBody AuthReqModel authReqModel) {
		System.out.println("authReqModel = " + authReqModel.toString());
		System.out.println("AuthController.login");
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authReqModel.getId(), authReqModel.getPassword()));

		String userId = authReqModel.getId();
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Date now = new Date();
		AuthToken accessToken = tokenProvider.createAuthToken(userId,
			((UserPrincipal)authentication.getPrincipal()).getRoleType().getCode(),
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

		long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
		AuthToken refreshToken = tokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(),
			new Date(now.getTime() + refreshTokenExpiry));

		// userId refresh token 으로 DB 확인
		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
		if (userRefreshToken == null) {
			// 없는 경우 새로 등록
			userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
			userRefreshTokenRepository.saveAndFlush(userRefreshToken);
		} else {
			// DB에 refresh 토큰 업데이트
			userRefreshToken.setRefreshToken(refreshToken.getToken());
		}

		int cookieMaxAge = (int)refreshTokenExpiry / 60;
		CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
		return ApiResponses.success(new TokenDto(accessToken.getToken()));
	}

	@Operation(summary = "Accesss Token 만료시 Refresh Token으로 Access Token 재요청", description = "Accesss Token 만료시 <br> 만료된 Access Token과  Refresh Token으로 token 재요청 <br> 쿠키에 refresh token, response body에 access token 새로 만들어서 return")
	@GetMapping("/refresh")
	public ApiResponses<TokenDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {
		// access token 확인
		String accessToken = HeaderUtil.getAccessToken(request);
		AuthToken authToken = tokenProvider.convertAuthToken(accessToken);

		// expired access token 인지 확인
		Claims claims = authToken.getExpiredTokenClaims();
		if (claims == null) {
			throw new CustomAuthTokenException(ResponseMsgType.NOT_EXPIRED_TOKEN_YET);

		}

		String userId = claims.getSubject();
		RoleType roleType = RoleType.of(claims.get("role", String.class));

		// refresh token
		String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN).map(Cookie::getValue).orElse((null));
		AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

		if (!authRefreshToken.validate(request)) {
			throw new CustomAuthTokenException(ResponseMsgType.INVALID_TOKEN, "Refresh");
		}

		// userId refresh token 으로 DB 확인
		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId,
			refreshToken);
		if (userRefreshToken == null) {
			throw new CustomAuthTokenException(ResponseMsgType.INVALID_TOKEN, "Refresh");
		}

		Date now = new Date();
		AuthToken newAccessToken = tokenProvider.createAuthToken(userId, roleType.getCode(),
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

		long validTime = authRefreshToken.getTokenClaims(request).getExpiration().getTime() - now.getTime();

		// refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
		if (validTime <= THREE_DAYS_MSEC) {
			// refresh 토큰 설정
			long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

			authRefreshToken = tokenProvider.createAuthToken(appProperties.getAuth().getTokenSecret(),
				new Date(now.getTime() + refreshTokenExpiry));

			// DB에 refresh 토큰 업데이트
			userRefreshToken.setRefreshToken(authRefreshToken.getToken());

			int cookieMaxAge = (int)refreshTokenExpiry / 60;
			CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
			CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
		}

		return ApiResponses.success(new TokenDto(newAccessToken.getToken()));
	}
}
