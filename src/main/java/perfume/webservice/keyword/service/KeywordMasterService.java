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
import perfume.webservice.keyword.domain.dto.save.KeywordSaveDto;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveDtoList;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.repository.KeywordQueryRepository;
import perfume.webservice.keyword.repository.KeywordRepository;
import perfume.webservice.keyword.searchcondition.KeywordSearchCondition;
import perfume.webservice.perfume.domain.entity.Perfume;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class KeywordMasterService {

    private final KeywordRepository keywordRepository;
    private final KeywordQueryRepository keywordQueryRepository;

    @Transactional
    public SavedResult saveKeyword(KeywordSaveDtoList requestDto) {

        SavedResult savedResult = new SavedResult();

        for (KeywordSaveDto keywordSaveDto : requestDto.getKeywordSaveList()) {
            if (keywordSaveDto.getId() == null) {
                savedResult.addInsertedId(keywordRepository.save(keywordSaveDto.toEntity()).getId());
            } else {
                KeywordMaster keywordMaster = keywordRepository.findById(keywordSaveDto.getId())
                        .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.KEYWORD_NOT_FOUND, keywordSaveDto.getId()));
                keywordMaster.update(keywordSaveDto);
                savedResult.addUpdatedId(keywordSaveDto.getId());
            }
        }
        return savedResult;
    }

    public Page<KeywordResponseDto> findAllKeywordPageDesc(KeywordSearchCondition condition, Pageable pageable) {
        Page<KeywordMaster> results = keywordQueryRepository.searchKeywordsWithCondition(condition, pageable);
        return results.map(keywordMaster -> new KeywordResponseDto(keywordMaster));

    }
}
