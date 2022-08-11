package perfume.webservice.perfume.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perfume.webservice.keyword.domain.entity.KeywordType;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.PerfumeKeyword;

@Getter
@RequiredArgsConstructor
public class PerfumeKeywordsResponseDto {

	@Schema(description = "키워드 id", example = "125")
	private final Long keywordId;
	@Schema(description = "키워드 명칭", example = "Red")
	private final String keywordName;
	@Schema(description = "키워드 타입", example = "COLOR")
	private final KeywordType keywordType;


	public PerfumeKeywordsResponseDto(PerfumeKeyword entity) {
		this.keywordId = entity.getKeywordMaster().getId();
		this.keywordName = entity.getKeywordMaster().getName();
		this.keywordType = entity.getKeywordMaster().getKeywordType();

	}
}