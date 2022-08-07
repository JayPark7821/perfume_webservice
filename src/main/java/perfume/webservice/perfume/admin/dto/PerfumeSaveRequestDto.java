package perfume.webservice.perfume.admin.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.Perfume;

@Getter
@NoArgsConstructor
public class PerfumeSaveRequestDto {

	@Schema(description = "향수 id ( 향수 id를 같이 보내면 update, 없으면 insert)")
	private Long id;
	@Valid
	@NotNull
	@Schema(description = "향수 명", example = "샤넬 No.5")
	private String name;
	@Valid
	@NotNull
	@Schema(description = "향수 설명", example = "이 향수는 ~~~")
	private String description;
	@Valid
	@NotNull
	private List<FragranceGroupSaveDto> fragrance;

	@Builder
	PerfumeSaveRequestDto(String name, String description, List<FragranceGroupSaveDto> fragranceList) {
		this.name = name;
		this.description = description;
		this.fragrance = Objects.requireNonNullElseGet(fragranceList, ArrayList::new);
	}

	public Perfume toEntity() {

		return Perfume.builder()
			.name(name)
			.description(description)
			.build();
	}
}
