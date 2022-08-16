package perfume.webservice.perfume.domain.dto.save;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FragranceGroupSaveDto {
	@Valid
	@NotNull
	@Schema(example = "향 id")
	private Long id;
	@Valid
	@NotNull
	@Max(100)
	@Min(0)
	@Schema(example = "해당향 함량 ex) 100")
	private int percentage;



}
