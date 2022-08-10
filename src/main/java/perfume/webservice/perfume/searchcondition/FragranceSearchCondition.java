package perfume.webservice.perfume.searchcondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class FragranceSearchCondition {
	@Schema(description = "검색 조건 ( 향 명칭 ) like 검색 '%fragranceName%'", example = "fresh")
	private String fragranceName;

	@Schema(description = "검색 조건 ( 향 특징 ) like 검색 '%fragranceDesc%'", example = "sooo freshhhhh")
	private String fragranceDesc;

	public FragranceSearchCondition(String fragranceName, String fragranceDesc ) {
		this.fragranceName = fragranceName;
		this.fragranceDesc = fragranceDesc;
	}


}
