package perfume.webservice.perfume.domain.dto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PerfumeFragranceSaveDto {

	@Valid
	@NotNull
	@Schema(description = "향 id", example = "112", required = true)
	private Long fragranceId;

	@Valid
	@NotNull
	@Schema(description = "향 함유량", example = "향 함유량", required = true)
	private int percentage;


}
