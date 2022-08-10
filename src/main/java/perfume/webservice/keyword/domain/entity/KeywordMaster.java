package perfume.webservice.keyword.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveDto;

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

    public void update(KeywordSaveDto keywordSaveDto) {
        this.name = keywordSaveDto.getName();
        this.description = keywordSaveDto.getDesc();
        this.keywordType = keywordSaveDto.getKeywordType();
    }
}
