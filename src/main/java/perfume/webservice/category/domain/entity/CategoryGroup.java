package perfume.webservice.category.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CategoryGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_group_id")
    private Long id;

    @Column(name = "category_group_name")
    private String name;

    @Column(name = "category_group_desc")
    private String desc;

    @OneToMany(mappedBy = "categoryGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryGroupDetail> categoryGroupDetail = new ArrayList<>();


    public void setCategoryGroupDetail(List<CategoryGroupDetail> categoryGroups) {
        this.categoryGroupDetail = categoryGroups;
        for (CategoryGroupDetail groupDetail : categoryGroupDetail) {
            groupDetail.setRelationWithCategoryGroup(this);
        }
    }

    public void update(Long id, String name, String desc, List<CategoryGroupDetail> categoryGroupDetail) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.setCategoryGroupDetail(categoryGroupDetail);
    }
    @Builder
    public CategoryGroup(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
