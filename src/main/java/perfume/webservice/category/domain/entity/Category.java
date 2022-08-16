package perfume.webservice.category.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.keyword.domain.entity.KeywordGroup;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_desc")
    private String description;

    @OneToOne
    @JoinColumn(name = "keyword_group_id")
    private KeywordGroup keywordGroup;


    @Builder
    public Category(String categoryName, String description, KeywordGroup keywordGroup) {
        this.name = categoryName;
        this.description = description;
        this.keywordGroup = keywordGroup;
    }

    public void update(String name, String desc, KeywordGroup keywordGroup) {
        this.name = name;
        this.description = desc;
        this.keywordGroup = keywordGroup;
    }
}
