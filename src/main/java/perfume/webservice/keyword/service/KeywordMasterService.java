package perfume.webservice.keyword.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.keyword.domain.dto.response.KeywordResponseDto;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDto;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDtoList;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.repository.KeywordQueryRepository;
import perfume.webservice.keyword.repository.KeywordRepository;
import perfume.webservice.keyword.searchcondition.KeywordSearchCondition;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class KeywordMasterService {

    private final KeywordRepository keywordRepository;
    private final KeywordQueryRepository keywordQueryRepository;

    @Transactional
    public SavedResult saveKeyword(KeywordSaveRequestDtoList requestDto) {

        SavedResult savedResult = new SavedResult();

        for (KeywordSaveRequestDto keywordSaveRequestDto : requestDto.getKeywordSaveList()) {
            if (keywordSaveRequestDto.getId() == null) {
                savedResult.addInsertedId(keywordRepository.save(keywordSaveRequestDto.toEntity()).getId());
            } else {
                KeywordMaster keywordMaster = keywordRepository.findById(keywordSaveRequestDto.getId())
                        .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.KEYWORD_NOT_FOUND, keywordSaveRequestDto.getId()));
                keywordMaster.update(keywordSaveRequestDto);
                savedResult.addUpdatedId(keywordSaveRequestDto.getId());
            }
        }
        return savedResult;
    }

    public Page<KeywordResponseDto> findAllKeywordPageDesc(KeywordSearchCondition condition, Pageable pageable) {
        Page<KeywordMaster> results = keywordQueryRepository.searchKeywordsWithCondition(condition, pageable);
        return results.map(keywordMaster -> new KeywordResponseDto(keywordMaster));

    }
}
