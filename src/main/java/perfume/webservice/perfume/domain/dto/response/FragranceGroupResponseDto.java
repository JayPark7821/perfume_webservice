package perfume.webservice.perfume.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perfume.webservice.perfume.domain.entity.FragranceGroup;

@Getter
@RequiredArgsConstructor
public class FragranceGroupResponseDto {

	@Schema(description = "향 id", example = "125")
	private final Long fragranceId;
	@Schema(description = "향 명칭", example = "fresh")
	private final String fragranceName;
	@Schema(description = "향 설명", example = "soooooo fresh")
	private final String fragranceDesc;

	@Schema(description = "향 함유량", example = "30")
	private final int containPercentage;

	public FragranceGroupResponseDto(FragranceGroup entity) {
		this.fragranceId = entity.getFragrance().getId();
		this.fragranceName = entity.getFragrance().getName();
		this.fragranceDesc = entity.getFragrance().getDescription();
		this.containPercentage = entity.getContainPercentage();
	}
}