package perfume.webservice.keyword.domain.dto.response;

import lombok.Getter;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.domain.entity.KeywordType;

@Getter
public class KeywordOptionResponseDto {
    private Long id;
    private String name;
    private String detail;
    private KeywordType keywordType;

    public KeywordOptionResponseDto(KeywordMaster entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.detail = entity.getDescription();
        this.keywordType = entity.getKeywordType();
    }
}
