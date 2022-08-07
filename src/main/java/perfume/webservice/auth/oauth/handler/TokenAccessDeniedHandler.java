package perfume.webservice.auth.oauth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perfume.webservice.common.dto.ApiResponses;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

	//    private final HandlerExceptionResolver handlerExceptionResolver;
	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {

		log.info("Responding with accessDenied error. Message := {}", accessDeniedException.getMessage());
		String result = objectMapper.writeValueAsString(
			ApiResponses.failResponse(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getLocalizedMessage()));
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);

		//response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
		//        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
	}
}