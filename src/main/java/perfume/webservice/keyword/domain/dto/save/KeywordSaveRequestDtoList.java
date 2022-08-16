package perfume.webservice.keyword.domain.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
public class KeywordSaveRequestDtoList {
    @Valid
    private List<KeywordSaveRequestDto> keywordSaveList;

}
