package perfume.webservice.auth.api.entity.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthReqModel {
	@Schema(example = "사용자 id")
	private String id;
	@Schema(example = "사용자 비밀번호")
	private String password;
}
