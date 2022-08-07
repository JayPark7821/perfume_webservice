package perfume.webservice.perfume.admin.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.Fragrance;

@Getter
@NoArgsConstructor
public class FragranceSaveRequestDto {

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
	FragranceSaveRequestDto(String name, String desc, List<FragranceGroupSaveDto> fragranceList) {
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
