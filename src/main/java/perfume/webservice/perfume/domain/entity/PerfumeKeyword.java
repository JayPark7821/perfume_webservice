package perfume.webservice.perfume.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.keyword.domain.entity.KeywordMaster;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@IdClass(PerfumeKeywordId.class)
public class PerfumeKeyword extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfume_id")
    private Perfume perfume;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private KeywordMaster keywordMaster;

    public void setPerfumeRelation(Perfume perfume) {
        this.perfume = perfume;
    }
}
