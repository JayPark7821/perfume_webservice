package perfume.webservice.category.searchcondition;

import lombok.Getter;

@Getter
public class CategorySearchCondition {

    private String categoryName;
    private String categoryDesc;
    private Long childCategoryId;
    private Long keywordGroupId;

}
