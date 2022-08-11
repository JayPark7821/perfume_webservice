package perfume.webservice.perfume.domain.dto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.Perfume;
import perfume.webservice.perfume.domain.entity.PerfumeKeyword;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

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
	private List<FragranceGroupSaveDto> fragranceGroup;

	@Valid
	@NotNull
	private List<Long> keyword;

	@Builder
    PerfumeSaveRequestDto(String name, String description, List<FragranceGroupSaveDto> fragranceGroup, List<Long> keyword) {
		this.name = name;
		this.description = description;
		this.fragranceGroup = fragranceGroup;
		this.keyword = keyword;
	}

	public Perfume toEntity() {
		return Perfume.builder()
				.name(name)
				.description(description)
				.build();
	}
}
