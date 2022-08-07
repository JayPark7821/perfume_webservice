package perfume.webservice.perfume.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.FragranceGroup;

@Getter
@NoArgsConstructor
public class FragranceGroupResponseDto {

	@Schema(description = "향 id", example = "125")
	private Long fragranceId;
	@Schema(description = "향 명칭", example = "fresh")
	private String fragranceName;
	@Schema(description = "향 설명", example = "soooooo fresh")
	private String fragranceDesc;

	@Schema(description = "향 함유량", example = "30")
	private int containPercentage;

	public FragranceGroupResponseDto(FragranceGroup entity) {
		this.fragranceId = entity.getFragrance().getId();
		this.fragranceName = entity.getFragrance().getName();
		this.fragranceDesc = entity.getFragrance().getDescription();
		this.containPercentage = entity.getContainPercentage();
	}
}