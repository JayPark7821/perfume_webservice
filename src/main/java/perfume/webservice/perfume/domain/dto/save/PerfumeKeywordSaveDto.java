package perfume.webservice.perfume.domain.dto.save;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perfume.webservice.keyword.domain.entity.KeywordType;

@Getter
@RequiredArgsConstructor
public class PerfumeKeywordSaveDto {
    private final Long id;
    private final String name;
    private final String keywordDetail;
    private final KeywordType keywordType;
}
