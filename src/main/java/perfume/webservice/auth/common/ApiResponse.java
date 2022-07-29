package perfume.webservice.auth.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final static int SUCCESS = 200;
    private final static int BAD_REQUEST = 400;
    private final static int FAILED = 500;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";

    private final static String BAD_REQUEST_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";

    private final int code;
    private final String message;
    private final Map<String, T> data;

    public static <T> ApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ApiResponse(SUCCESS, SUCCESS_MESSAGE, map);
    }

    public static <T> ApiResponse<T> failCustomResponse(int headerCode, String headerMsg) {
        return new ApiResponse(headerCode, headerMsg, null);
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse(FAILED, FAILED_MESSAGE, null);
    }

    public static <T> ApiResponse<T> bindingFail() {
        return new ApiResponse(BAD_REQUEST, BAD_REQUEST_MESSAGE, null);
    }

    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse(FAILED, INVALID_ACCESS_TOKEN, null);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse(FAILED, INVALID_REFRESH_TOKEN, null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse(FAILED, NOT_EXPIRED_TOKEN_YET, null);
    }
}
