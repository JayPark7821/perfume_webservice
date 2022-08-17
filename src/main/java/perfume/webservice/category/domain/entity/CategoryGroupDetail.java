package perfume.webservice.category.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.keyword.domain.entity.KeywordGroupDetailId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@IdClass(CategoryGroupDetailId.class)
public class CategoryGroupDetail extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_group_id")
    private CategoryGroup categoryGroup;

    private int level;

    public void setRelationWithCategoryGroup(CategoryGroup categoryGroup) {
        this.categoryGroup = categoryGroup;
    }


    @Builder
    public CategoryGroupDetail(Category category, CategoryGroup categoryGroup, int level) {
        this.category = category;
        this.categoryGroup = categoryGroup;
        this.level = level;
    }
}
