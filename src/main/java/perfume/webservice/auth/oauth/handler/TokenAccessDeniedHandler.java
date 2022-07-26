package perfume.webservice.auth.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import perfume.webservice.auth.common.ApiResponse;
import perfume.webservice.auth.common.ApiResponseHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

//    private final HandlerExceptionResolver handlerExceptionResolver;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        log.info("Responding with accessDenied error. Message := {}", accessDeniedException.getMessage());
        String result = objectMapper.writeValueAsString(
                new ApiResponse(new ApiResponseHeader(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getLocalizedMessage()), null));
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);

        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
//        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}