package perfume.webservice.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.category.domain.dto.save.*;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.category.domain.entity.CategoryGroup;
import perfume.webservice.category.domain.entity.CategoryGroupDetail;
import perfume.webservice.category.repository.CategoryGroupDetailRepository;
import perfume.webservice.category.repository.CategoryGroupRepository;
import perfume.webservice.category.repository.CategoryQueryRepository;
import perfume.webservice.category.repository.CategoryRepository;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.common.utils.ParamValidator;
import perfume.webservice.perfume.repository.KeywordGroupRepository;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryGroupRepository categoryGroupRepository;
    private final CategoryGroupDetailRepository categoryGroupDetailRepository;
    private final KeywordGroupRepository keywordGroupRepository;
    private final CategoryQueryRepository categoryQueryRepository;
    private final ParamValidator paramValidator;

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

                category.update(requestDto.getName(), requestDto.getDesc()
                        , keywordGroupRepository.findById(requestDto.getKeywordGroupId())
                                .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.KEYWORDGROUP_NOT_FOUND, requestDto.getId())));
                savedResult.addUpdatedId(category.getId());
            }
        }
        return savedResult;
    }

    private Category getSubCategoriesByIds(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->  new CustomIllegalArgumentException(ResponseMsgType.SUBCATEGORY_NOT_FOUND, id));
    }

    @Transactional
    public SavedResult saveCategoryGroup(CategoryGroupSaveRequestDtoList requestDtoList) {

        SavedResult savedResult = new SavedResult();
        for (CategoryGroupSaveRequestDto requestDto : requestDtoList.getCategoryGroupSaveRequestDto()) {
            if (requestDto.getId() == null) {
                List<Long> categoryIds = requestDto.getCategoryGroupDetail().stream().map(CategoryGroupDetailSaveRequestDto::getCategoryId).collect(Collectors.toList());
                List<Category> categoryList = categoryRepository.findByIds(categoryIds);
                paramValidator.validateDupliAndExsistParams(categoryIds, categoryList.stream().map(Category::getId).collect(Collectors.toList()), "CATEGORY");
                CategoryGroup categoryGroup = CategoryGroup.builder()
                        .name(requestDto.getName())
                        .desc(requestDto.getDesc())
                        .build();
                CategoryGroup savedCategoryGroup = categoryGroupRepository.save(categoryGroup);


                categoryList.stream().map(c -> CategoryGroupDetail.builder()
                                .categoryGroup(savedCategoryGroup)
                                .category(c)
                                .level(requestDto.getCategoryGroupDetail().stream()
                                        .filter(d -> Objects.equals(d.getCategoryId(), c.getId()))
                                        .findFirst()
                                        .orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.CONTAIN_PERCENTAGE_NOT_FOUND,
                                                savedCategoryGroup.getName()))
                                        .getLevel())
                                .build())
                        .forEach(categoryGroupDetailRepository::save);

                savedResult.addInsertedId(savedCategoryGroup.getId());
            }

        }
        return savedResult;
    }


//    public Object findCategoriesByCondition(CategorySearchCondition condition, Pageable pageable) {
//        Page<Category> results = categoryQueryRepository. (condition, pageable);
//        return results.map(keywordMaster -> new KeywordResponseDto(keywordMaster));
//    }
}
