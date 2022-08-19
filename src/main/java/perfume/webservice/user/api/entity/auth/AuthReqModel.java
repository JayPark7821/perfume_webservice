package perfume.webservice.user.api.entity.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthReqModel {

	@Schema(example = "사용자 id")
	@NotEmpty
	private String id;

	@Schema(example = "사용자 비밀번호")
	@NotEmpty
	private String password;

}
