package perfume.webservice.keyword.domain.dto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.domain.entity.KeywordType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class KeywordSaveRequestDto {

    @Schema(description = "키워드 id")
    private Long id;

    @Valid
    @NotNull
    @Schema(description = "키워드 명칭", example = "키워드의 명칭", required = true)
    private String name;

    @Valid
    @NotNull
    @Schema(description = "키워드 설명", example = "키워드의 설명", required = true)
    private String desc;

    @Valid
    @NotNull
    @Schema(description = "키워드 설명", example = "키워드의 설명", required = true)
    private KeywordType keywordType;


    @Builder
    public KeywordSaveRequestDto(String name, String desc, KeywordType keywordType) {
        this.name = name;
        this.desc = desc;
        this.keywordType = keywordType;
    }

    public KeywordMaster toEntity() {
        return KeywordMaster.builder()
                .name(name)
                .description(desc)
                .keywordType(keywordType)
                .build();
    }
}
