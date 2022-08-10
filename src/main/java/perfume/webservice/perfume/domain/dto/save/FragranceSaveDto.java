package perfume.webservice.perfume.domain.dto.save;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.domain.entity.Fragrance;

@Getter
@NoArgsConstructor
public class FragranceSaveDto {

	@Schema(description = "향 id")
	private Long id;
	@Valid
	@NotNull
	@Schema(description = "향 명칭", example = "향의 명칭", required = true)
	private String name;
	@Valid
	@NotNull
	@Schema(description = "향 설명", example = "향의 설명", required = true)
	private String desc;

	@Builder
	FragranceSaveDto(String name, String desc ) {
		this.name = name;
		this.desc = desc;
	}

	public Fragrance toEntity() {
		return Fragrance.builder()
			.name(name)
			.description(desc)
			.build();
	}

}
