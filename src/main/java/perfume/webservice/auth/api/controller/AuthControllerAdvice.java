package perfume.webservice.auth.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import perfume.webservice.auth.api.controller.auth.AuthController;
import perfume.webservice.auth.common.ApiResponse;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ApiResponse handleEntityNotFoundException(BadCredentialsException e) {
        return ApiResponse.failCustomResponse(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage());
    }
}
