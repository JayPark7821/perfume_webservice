package perfume.webservice.perfume.searchcondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PerfumeSearchCondition {
	@Schema(description = "검색 조건 ( 향수 명칭 ) like 검색 '%perfumeName%'", example = "샤넬 No.5")
	private String perfumeName;
	@Schema(description = "검색 조건 ( 향 명칭 ) like 검색 '%fragranceName%'", example = "fresh")
	private String fragranceName;

	public PerfumeSearchCondition(String perfumeName, String fragranceName) {
		this.perfumeName = perfumeName;
		this.fragranceName = fragranceName;
	}


}
