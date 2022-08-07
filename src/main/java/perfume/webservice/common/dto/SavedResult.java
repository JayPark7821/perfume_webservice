package perfume.webservice.common.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SavedResult {

	@Schema(example = "save된 id값")
	private List<Long> inserted;
	@Schema(example = "update된 id값")
	private List<Long> updated;

	private int totalCount;

}
