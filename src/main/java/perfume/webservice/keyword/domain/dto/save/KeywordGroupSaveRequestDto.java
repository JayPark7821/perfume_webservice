package perfume.webservice.keyword.domain.dto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.keyword.domain.entity.KeywordGroup;
import perfume.webservice.keyword.domain.entity.KeywordGroupDetail;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.domain.entity.KeywordType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class KeywordGroupSaveRequestDto {

    @Schema(description = "키워드그룹 id")
    private Long id;

    @Valid
    @NotNull
    @Schema(description = "키워드의 id", example = "11", required = true)
    private List<Long> keywordId;

    @Valid
    @NotNull
    @Schema(description = "키워드의 타입", example = "COLOR", required = true)
    private KeywordType keywordType;

// 키워드 그룹 저장 -> 아이디 가져와서 -> 키워드그룹디테일 생성 -> 키워드그룹 다시저장
    public KeywordGroup toEntity(KeywordType keywordType) {
        return KeywordGroup.builder()
                .keywordType(keywordType)
                .build();
    }
}
