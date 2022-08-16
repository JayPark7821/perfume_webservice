package perfume.webservice.keyword.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.common.utils.ParamValidator;
import perfume.webservice.keyword.domain.dto.save.KeywordGroupSaveRequestDto;
import perfume.webservice.keyword.domain.dto.save.KeywordGroupSaveRequestDtoList;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDto;
import perfume.webservice.keyword.domain.entity.KeywordGroup;
import perfume.webservice.keyword.domain.entity.KeywordGroupDetail;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.repository.KeywordRepository;
import perfume.webservice.perfume.repository.KeywordGroupRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordGroupService {

    private final KeywordGroupRepository keywordGroupRepository;
    private final KeywordRepository keywordRepository;
    private final ParamValidator paramValidator;

    @Transactional
    public SavedResult saveKeywordGroup(KeywordGroupSaveRequestDtoList requestDtos) {
        SavedResult savedResult = new SavedResult();

        for (KeywordGroupSaveRequestDto requestDto : requestDtos.getKeywordGroupList()) {
            if (requestDto.getId() == null) {
                KeywordGroup keywordGroup = keywordGroupRepository.save(requestDto.toEntity(requestDto.getKeywordType()));
                List<KeywordGroupDetail> groupDetails = makeKeywordGroupDetail(requestDto, keywordGroup);
                keywordGroup.update(groupDetails, requestDto.getKeywordType());
                savedResult.addInsertedId(keywordGroup.getId());
            }else{
                KeywordGroup keywordGroup = keywordGroupRepository.findById(requestDto.getId())
                        .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.KEYWORDGROUP_NOT_FOUND, requestDto.getId()));
                List<KeywordGroupDetail> groupDetails = makeKeywordGroupDetail(requestDto, keywordGroup);
                keywordGroup.update(groupDetails, requestDto.getKeywordType());
                savedResult.addUpdatedId(keywordGroup.getId());
            }
        }

        return savedResult;
    }

    private List<KeywordGroupDetail> makeKeywordGroupDetail(KeywordGroupSaveRequestDto requestDto, KeywordGroup keywordGroup) {
        List<KeywordMaster> keywordMasters = keywordRepository.findByIds(requestDto.getKeywordId());
        paramValidator.validateDupliAndExsistParams(requestDto.getKeywordId(),keywordMasters.stream().map(KeywordMaster::getId).collect(Collectors.toList()), "KEYWORD");

        List<KeywordGroupDetail> groupDetails = keywordMasters.stream().map(k -> KeywordGroupDetail.builder()
                .keywordGroup(keywordGroup)
                .keywordMaster(k)
                .build()).collect(Collectors.toList());
        return groupDetails;
    }

//    public Object findAllKeywordGroup(KeywordSearchCondition condition, Pageable pageable) {
//    }
}
