package perfume.webservice.user.oauth.exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import perfume.webservice.user.oauth.entity.AuthExceptionType;
import perfume.webservice.common.dto.ApiResponses;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException
	) throws IOException, ServletException {
		AuthExceptionType exception = (AuthExceptionType)request.getAttribute("exception");
		System.out.println("exception = " + exception.getCode());
		log.info("Responding with Exception. Message := {} , {}", exception.getCode(), exception.getDisplayName());
		log.info("Responding with unauthorized error. Message := {}", authException.getMessage());
		String result = objectMapper.writeValueAsString(
			ApiResponses.failResponse(HttpServletResponse.SC_UNAUTHORIZED, exception.getDisplayName()));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.getWriter().write(result);

		//        authException.printStackTrace();
		//        response.sendError(
		//                HttpServletResponse.SC_UNAUTHORIZED,
		//                authException.getLocalizedMessage()
		//        );
	}
}
