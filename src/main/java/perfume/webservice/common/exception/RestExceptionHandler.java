package perfume.webservice.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import perfume.webservice.auth.common.ApiResponse;

@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE) // 우선순위를 가장 높게
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private ResponseEntity<Object> buildResponseEntity(ApiResponse<Object> apiResponse) {
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    protected ResponseEntity<Object> handleCustomIllegalArgumentException(CustomIllegalArgumentException ex) {
        String message = messageSource.getMessage(ex.getLocalizedMessage(), new Object[]{ex.getParam()}, null);
        ApiResponse<Object> apiResponse = ApiResponse.failResponse(HttpStatus.BAD_REQUEST.value(),message);
        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(CustomAuthTokenException.class)
    protected ResponseEntity<Object> handleCustomAuthTokenException(CustomAuthTokenException ex) {
        String message = messageSource.getMessage(ex.getLocalizedMessage(), new Object[]{ex.getParam()}, null);
        ApiResponse<Object> apiResponse = ApiResponse.failResponse(HttpStatus.FORBIDDEN.value(), message);
        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(CustomBindingException.class)
    protected ResponseEntity<Object> handleCustomBindingException(CustomBindingException ex) {
        String message = messageSource.getMessage(ex.getLocalizedMessage(), new Object[]{ex.getParam()}, null);
        ApiResponse<Object> apiResponse = ApiResponse.failResponse(HttpStatus.BAD_REQUEST.value(), message);
        return buildResponseEntity(apiResponse);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ApiResponse handleBadCredentialsException(BadCredentialsException e) {
        return ApiResponse.failResponse(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage());
    }

}