package perfume.webservice.category.domain.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryGroupDetailSaveRequestDto {

    private Long categoryId;
    private int level;

}
