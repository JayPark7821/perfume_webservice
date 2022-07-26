package perfume.webservice.auth.oauth.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.ModelAndView;
import perfume.webservice.auth.common.ApiResponse;
import perfume.webservice.auth.common.ApiResponseHeader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        log.info("Responding with unauthorized error. Message := {}", authException.getMessage());
        String result = objectMapper.writeValueAsString(
                new ApiResponse(new ApiResponseHeader(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage()), null));
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);

//        authException.printStackTrace();
//        response.sendError(
//                HttpServletResponse.SC_UNAUTHORIZED,
//                authException.getLocalizedMessage()
//        );
    }
}
