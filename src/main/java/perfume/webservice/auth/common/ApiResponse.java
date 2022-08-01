package perfume.webservice.auth.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private final Map<String, T> data;

    private ApiResponse(int code, String message, Map<String, T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new ApiResponse(200, "SUCCESS", map);
    }

    public static <T> ApiResponse<T> failResponse(int headerCode, String headerMsg) {
        return new ApiResponse(headerCode, headerMsg, null);
    }
}
