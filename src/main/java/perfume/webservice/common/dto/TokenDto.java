package perfume.webservice.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TokenDto {

	@Schema(example = "토큰 값")
	private String token;

	public TokenDto(String token) {
		this.token = token;
	}
}
