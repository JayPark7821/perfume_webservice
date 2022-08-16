package perfume.webservice.perfume.domain.dto.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.Perfume;
import perfume.webservice.perfume.domain.entity.PerfumeKeyword;

@Getter
public class PerfumeResponseDto {

	@Schema(description = "향수 id", example = "125")
	private Long id;
	@Schema(description = "향수 명", example = "le labo santal 33")
	private String name;
	@Schema(description = "향수 설명", example = "르라보 쌍탈 33")
	private String description;

	private List<FragranceGroupResponseDto> fragranceGroupResponseDtoList = new ArrayList<>();

	private List<PerfumeKeywordsResponseDto> perfumeKeywordsResponseDtoList = new ArrayList<>();


	public void addFragranceGroupResponseDto(FragranceGroupResponseDto fragrance) {
		this.fragranceGroupResponseDtoList.add(fragrance);
	}

	public void addKeywordsResponseDto(PerfumeKeywordsResponseDto keyword) {
		this.perfumeKeywordsResponseDtoList.add(keyword);
	}


	public PerfumeResponseDto(Perfume entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		if (entity.getFragranceGroup().size() == 0) {
			this.fragranceGroupResponseDtoList = new ArrayList<>();
		} else {
			for (FragranceGroup fragrance : entity.getFragranceGroup()) {
				this.addFragranceGroupResponseDto(new FragranceGroupResponseDto(fragrance));
			}
		}
		if (entity.getPerfumeKeyword().size() == 0) {
			this.perfumeKeywordsResponseDtoList = new ArrayList<>();
		} else {
			for (PerfumeKeyword keyword : entity.getPerfumeKeyword()) {
				this.addKeywordsResponseDto(new PerfumeKeywordsResponseDto(keyword));
			}
		}

	}
}