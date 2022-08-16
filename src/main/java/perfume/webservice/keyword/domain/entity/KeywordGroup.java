package perfume.webservice.keyword.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class KeywordGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_group_id")
    private Long id;

    @OneToMany(mappedBy = "keywordGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KeywordGroupDetail> keywordGroupDetail = new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private KeywordType keywordType;


    public void setKeyword(List<KeywordGroupDetail> keywordGroupDetail) {
        this.keywordGroupDetail = keywordGroupDetail;
        for (KeywordGroupDetail groupDetail : keywordGroupDetail) {
            groupDetail.setKeywordGroupDetail(this);
        }
    }


    @Builder
    public KeywordGroup( KeywordType keywordType) {
        this.keywordType = keywordType;
    }

    public void update( List<KeywordGroupDetail> keywordGroupDetail, KeywordType keywordType ) {
        this.setKeyword( keywordGroupDetail);
        this.keywordType = keywordType;
    }

}
