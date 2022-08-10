package perfume.webservice.keyword.searchcondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import perfume.webservice.keyword.domain.entity.KeywordType;

@Getter
public class KeywordSearchCondition {
    @Schema(description = "검색 조건 ( 키워드 명칭 ) like 검색 '%keywordName%'", example = "Red")
    private String keywordName;

    @Schema(description = "검색 조건 ( 키워드 설명 ) like 검색 '%keywordDesc%'", example = "향의 색이 red")
    private String keywordDesc;

    @Schema(description = "검색 조건 ( 키워드 타입 ) like 검색 '%keywordType%'", example = "COLOR")
    private KeywordType keywordType;


    public KeywordSearchCondition(String keywordName, String keywordDesc, KeywordType keywordType) {
        this.keywordName = keywordName;
        this.keywordDesc = keywordDesc;
        this.keywordType = keywordType;
    }
    
}
