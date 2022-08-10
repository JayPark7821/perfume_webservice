package perfume.webservice.common.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavedResult {

	@Schema(example = "save된 id값")
	private List<Long> inserted = new ArrayList<>();
	@Schema(example = "update된 id값")
	private List<Long> updated = new ArrayList<>();

	private int totalCount;

	public void addInsertedId(Long id) {
		this.inserted.add(id);
		this.totalCount++;
	}
	public void addUpdatedId(Long id) {
		this.updated.add(id);
		this.totalCount++;
	}


}
