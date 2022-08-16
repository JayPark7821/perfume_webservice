package perfume.webservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.category.domain.dto.save.CategorySaveRequestDto;
import perfume.webservice.category.domain.dto.save.CategorySaveRequestDtoList;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.category.repository.CategoryQueryRepository;
import perfume.webservice.category.repository.CategoryRepository;
import perfume.webservice.category.searchcondition.CategorySearchCondition;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.common.utils.ParamValidator;
import perfume.webservice.keyword.domain.dto.response.KeywordResponseDto;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.repository.KeywordRepository;
import perfume.webservice.perfume.repository.KeywordGroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryAdminService {

    private final CategoryRepository categoryRepository;
    private final KeywordGroupRepository keywordGroupRepository;
    private final ParamValidator paramValidator;

    private final CategoryQueryRepository categoryQueryRepository;

    @Transactional
    public SavedResult saveCategory(CategorySaveRequestDtoList requestDtos) {

        SavedResult savedResult = new SavedResult();
        for (CategorySaveRequestDto requestDto : requestDtos.getCategorySaveRequestDto()) {
            if (requestDto.getId() == null) {
                Category category = categoryRepository.save(requestDto.toEntity(requestDto.getName(), requestDto.getDesc()
                        , requestDto.getKeywordGroupId() != null ? keywordGroupRepository.findById(requestDto.getKeywordGroupId())
                                .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.KEYWORDGROUP_NOT_FOUND, requestDto.getKeywordGroupId())) : null
                ));

                savedResult.addInsertedId(category.getId());
            } else {

                Category category = categoryRepository.findById(requestDto.getId())
                        .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.CATEGORY_NOT_FOUND, requestDto.getId()));

//                category.update(requestDto.getName(), requestDto.getDesc()
//                        , keywordGroupRepository.findById(requestDto.getKeywordGroupId())
//                                .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.KEYWORDGROUP_NOT_FOUND, requestDto.getId()))
//                        , getSubCategoriesByIds(requestDto.getSubCategoryId()));

                savedResult.addUpdatedId(category.getId());
            }
        }
        return savedResult;
    }

    private Category getSubCategoriesByIds(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->  new CustomIllegalArgumentException(ResponseMsgType.SUBCATEGORY_NOT_FOUND, id));
    }


//    public Object findCategoriesByCondition(CategorySearchCondition condition, Pageable pageable) {
//        Page<Category> results = categoryQueryRepository. (condition, pageable);
//        return results.map(keywordMaster -> new KeywordResponseDto(keywordMaster));
//    }
}
