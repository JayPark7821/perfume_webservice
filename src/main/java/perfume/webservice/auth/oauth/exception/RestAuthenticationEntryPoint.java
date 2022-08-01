package perfume.webservice.auth.oauth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import perfume.webservice.auth.common.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import perfume.webservice.auth.oauth.entity.AuthExceptionType;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        AuthExceptionType exception = (AuthExceptionType) request.getAttribute("exception");
        System.out.println("exception = " + exception.getCode());
        log.info("Responding with Exception. Message := {} , {}", exception.getCode() , exception.getDisplayName());
        log.info("Responding with unauthorized error. Message := {}", authException.getMessage());
        String result = objectMapper.writeValueAsString(
                ApiResponse.failResponse(HttpServletResponse.SC_UNAUTHORIZED, exception.getDisplayName()));
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
