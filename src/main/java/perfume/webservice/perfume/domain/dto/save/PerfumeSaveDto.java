package perfume.webservice.perfume.domain.dto.save;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.Perfume;
import perfume.webservice.perfume.domain.entity.PerfumeKeyword;

@Getter
@NoArgsConstructor
public class PerfumeSaveDto {

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
	private List<FragranceGroup> fragranceGroup;

	@Valid
	@NotNull
	private List<PerfumeKeyword> keyword;

	@Builder
	PerfumeSaveDto(String name, String description, List<FragranceGroup> fragranceGroup, List<PerfumeKeyword> keyword) {

		this.name = name;
		this.description = description;
		this.fragranceGroup = fragranceGroup;
		this.keyword = keyword;
	}
//
//	public Perfume toEntity() {
//		return Perfume.builder()
//				.name(name)
//				.description(description)
//				.fragranceGroup(fragranceGroup)
//				.perfumeKeyword(keyword)
//				.build();
//	}
}
