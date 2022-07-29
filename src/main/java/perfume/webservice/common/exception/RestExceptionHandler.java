package perfume.webservice.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import perfume.webservice.auth.common.ApiResponse;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE) // 우선순위를 가장 높게
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource ms;

    private ResponseEntity<Object> buildResponseEntity(ApiResponse<Object> apiResponse) {
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    protected ResponseEntity<Object> handleCustomIllegalArgumentException(CustomIllegalArgumentException ex) {
        String message = ms.getMessage(ex.getLocalizedMessage(), new Object[]{ex.getParam()}, null);
        ApiResponse<Object> apiResponse = ApiResponse.failCustomResponse(400, message);
        return buildResponseEntity(apiResponse);
    }

}