package perfume.webservice.keyword.domain.dto.save;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
public class KeywordSaveDtoList {
    @Valid
    private List<KeywordSaveDto> keywordSaveList;

    @Builder
    public KeywordSaveDtoList(List<KeywordSaveDto> keywordSaveList) {
        this.keywordSaveList = keywordSaveList;

    }

}
