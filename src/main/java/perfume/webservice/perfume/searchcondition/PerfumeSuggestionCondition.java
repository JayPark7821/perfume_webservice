package perfume.webservice.perfume.searchcondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class PerfumeSuggestionCondition {

	private List<Long> keywords;
}
