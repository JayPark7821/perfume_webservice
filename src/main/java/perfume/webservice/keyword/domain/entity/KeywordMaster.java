package perfume.webservice.keyword.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDto;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class KeywordMaster extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(name = "keyword_name")
    private String name;

    @Column(name = "keyword_desc")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private KeywordType keywordType;

    @Builder
    public KeywordMaster( String name, String description, KeywordType keywordType) {
        this.name = name;
        this.description = description;
        this.keywordType = keywordType;
    }

    public void update(KeywordSaveRequestDto keywordSaveRequestDto) {
        this.name = keywordSaveRequestDto.getName();
        this.description = keywordSaveRequestDto.getDesc();
        this.keywordType = keywordSaveRequestDto.getKeywordType();
    }
}
