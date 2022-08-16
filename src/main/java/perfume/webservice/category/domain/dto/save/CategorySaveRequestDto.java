package perfume.webservice.category.domain.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.keyword.domain.entity.KeywordGroup;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategorySaveRequestDto {

    private Long id;

    private String name;

    private String desc;

    private Long keywordGroupId;


    public Category toEntity(String name, String desc, KeywordGroup keywordGroup) {
        return Category.builder()
                .categoryName(name)
                .description(desc)
                .keywordGroup(keywordGroup)
                .build();

    }

}
