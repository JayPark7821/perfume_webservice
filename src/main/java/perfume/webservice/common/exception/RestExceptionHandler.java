package perfume.webservice.common.exception;

import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;
import perfume.webservice.common.dto.ApiResponses;

@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE) // 우선순위를 가장 높게
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	private ResponseEntity<Object> buildResponseEntity(ApiResponses<Object> apiResponses) {
		return new ResponseEntity<>(apiResponses, HttpStatus.OK);
	}

	@ExceptionHandler(CustomIllegalArgumentException.class)
	public ResponseEntity<Object> handleCustomIllegalArgumentException(CustomIllegalArgumentException ex) {
		String message = messageSource.getMessage(ex.getLocalizedMessage(), new Object[] {ex.getParam()}, null);
		ApiResponses<Object> apiResponses = ApiResponses.failResponse(HttpStatus.BAD_REQUEST.value(), message);
		return buildResponseEntity(apiResponses);
	}

	@ExceptionHandler(CustomAuthTokenException.class)
	public ResponseEntity<Object> handleCustomAuthTokenException(CustomAuthTokenException ex) {
		String message = messageSource.getMessage(ex.getLocalizedMessage(), new Object[] {ex.getParam()}, null);
		ApiResponses<Object> apiResponses = ApiResponses.failResponse(HttpStatus.FORBIDDEN.value(), message);
		return buildResponseEntity(apiResponses);
	}

	@ExceptionHandler(CustomBindingException.class)
	public ResponseEntity<Object> handleCustomBindingException(CustomBindingException ex) {
		String message = messageSource.getMessage(ex.getLocalizedMessage(), new Object[] {ex.getParam()}, null);
		ApiResponses<Object> apiResponses = ApiResponses.failResponse(HttpStatus.BAD_REQUEST.value(), message);
		return buildResponseEntity(apiResponses);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
		ApiResponses<Object> apiResponses = ApiResponses.failResponse(HttpStatus.FORBIDDEN.value(),
			ex.getLocalizedMessage());
		return buildResponseEntity(apiResponses);
	}

}