package perfume.webservice.common.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import perfume.webservice.auth.api.entity.user.User;

@Getter
public class ApiResponses<T> {

	@Schema(description = "상태 코드", example = "응답 상태 코드")
	private final int code;
	@Schema(description = "상태 메시지", example = "응답 상태 메시지")
	private final String message;
	@Schema(description = "데이터", oneOf = {TokenDto.class, SavedResult.class, User.class})
	private final T data;//Map<String, T> data;

	private ApiResponses(int code, String message, T data) { //Map<String, T> data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponses<T> success(T body) {
		return new ApiResponses<T>(HttpStatus.OK.value(), "SUCCESS", body);
	}

	public static <T> ApiResponses<T> failResponse(int headerCode, String headerMsg) {
		return new ApiResponses<T>(headerCode, headerMsg, null);
	}
}
