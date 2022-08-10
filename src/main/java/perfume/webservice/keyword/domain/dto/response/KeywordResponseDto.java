package perfume.webservice.keyword.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.domain.entity.KeywordType;
import perfume.webservice.perfume.domain.dto.response.FragranceGroupResponseDto;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.Perfume;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
public class KeywordResponseDto {
    
    @Schema(description = "키워드 id", example = "125")
    private Long id;

    @Schema(description = "키워드 명", example = "직업")
    private String name;

    @Schema(description = "키워드 설명", example = "향수의 직업 키워드")
    private String description;

    @Schema(description = "키워드 설명", example = "sales")
    private KeywordType keywordType;

    public KeywordResponseDto(KeywordMaster entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.keywordType = entity.getKeywordType();

    }
        
}
