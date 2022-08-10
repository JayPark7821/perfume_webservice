package perfume.webservice.keyword.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@IdClass(KeywordGroupDetailId.class)
public class KeywordGroupDetail extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_group_id")
    private KeywordGroup keywordGroup;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private KeywordMaster keywordMaster;

}
