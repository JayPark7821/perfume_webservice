package perfume.webservice.category.domain.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.category.domain.entity.CategoryGroup;
import perfume.webservice.category.domain.entity.CategoryGroupDetail;
import perfume.webservice.keyword.domain.entity.KeywordGroup;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryGroupSaveRequestDto {

    private Long id;

    private String name;

    private String desc;


    private List<CategoryGroupDetailSaveRequestDto> categoryGroupDetail;

//    public CategoryGroup toEntity(String name, String desc, List<CategoryGroupDetailSaveRequestDto> categoryGroupDetail) {
//        return CategoryGroup.builder()
//                .name(name)
//                .desc(desc)
//                .keywordGroup(keywordGroup)
//                .build();
//
//    }
}
