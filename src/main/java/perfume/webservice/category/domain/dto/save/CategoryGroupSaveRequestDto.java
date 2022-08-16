package perfume.webservice.category.domain.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.category.domain.entity.CategoryGroupDetail;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryGroupSaveRequestDto {

    private Long id;

    private String name;

    private int level;

    private List<Long> categoryIds;


}
