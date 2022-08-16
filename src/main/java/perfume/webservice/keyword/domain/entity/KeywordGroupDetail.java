package perfume.webservice.keyword.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Getter
@IdClass(KeywordGroupDetailId.class)
public class KeywordGroupDetail extends BaseTimeEntity  {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_group_id")
    private KeywordGroup keywordGroup;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private KeywordMaster keywordMaster;

    public void setKeywordGroupDetail(KeywordGroup keywordGroup) {
        this.keywordGroup = keywordGroup;
    }

    @Builder
    public KeywordGroupDetail(KeywordGroup keywordGroup, KeywordMaster keywordMaster) {
        this.keywordGroup = keywordGroup;
        this.keywordMaster = keywordMaster;
    }
}
