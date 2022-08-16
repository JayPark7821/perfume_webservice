package perfume.webservice.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.category.domain.dto.save.CategorySaveRequestDtoList;
import perfume.webservice.category.searchcondition.CategorySearchCondition;
import perfume.webservice.category.service.CategoryAdminService;
import perfume.webservice.common.dto.ApiResponses;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomBindingException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.keyword.domain.dto.response.KeywordResponseDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @PostMapping("/category")
    public ApiResponses<SavedResult> addCategory(@RequestBody @Valid CategorySaveRequestDtoList requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || requestDto == null ) {
            log.error("---------------------- SAVE CATEGORY BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_CATEGORY);
        }
        return ApiResponses.success(categoryAdminService.saveCategory(requestDto));
    }



//    @Operation(summary = "카테고리 정보 조회 (pageable)", description = "카테고리 정보 조회 <br> <br>1. 검색 조건 <br>  <br> <br> 2. 페이징 <br>  - 페이지당 출력할 데이터 건수 : size <br> - 조회할 페이지: page")
//    @GetMapping("/category")
//    public ApiResponses<Page<KeywordResponseDto>> findCategoriesByCondition(@RequestBody CategorySearchCondition condition, Pageable pageable) {
//        return ApiResponses.success(categoryAdminService.findCategoriesByCondition(condition, pageable));
//    }

}
