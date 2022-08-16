package perfume.webservice.keyword.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.common.dto.ApiResponses;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomBindingException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.keyword.domain.dto.response.KeywordResponseDto;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDtoList;
import perfume.webservice.keyword.domain.entity.KeywordType;
import perfume.webservice.keyword.searchcondition.KeywordSearchCondition;
import perfume.webservice.keyword.service.KeywordMasterService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "KeywordMasterController", description = "키워드 관련 정보 등록 및 조회 (관리자)")
public class KeywordMasterController {

    private final KeywordMasterService keywordMasterService;

    @Operation(summary = "키워드 정보 저장(insert & update)", description = "키워드 정보 저장처리 <br> <br>  insert와 update, 단 건과 다 건 모두 /api/admin/keyword 에서 처리한다.<br> insert와 update의 기준은 id값이며 id가 있으면 update 없으면 insert 한다 <br> <br> 키워드의 이름은 중복될 수 없다.")
    @PostMapping("/keyword")
    public ApiResponses<SavedResult> saveKeyword(@RequestBody @Valid KeywordSaveRequestDtoList requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || requestDto.getKeywordSaveList() == null || requestDto.getKeywordSaveList().size() == 0 ) {
            log.error("---------------------- SAVE KEYWORD BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_PERFUME);
        }
        return ApiResponses.success(keywordMasterService.saveKeyword(requestDto));
    }

    @Operation(summary = "키워드 정보 조회 (pageable)", description = "키워드 정보 조회 <br> <br>1. 검색 조건 <br>  - 키워드명 : perfumeName <br> - 향명: fragranceName)  <br> <br> 2. 페이징 <br>  - 페이지당 출력할 데이터 건수 : size <br> - 조회할 페이지: page")
    @GetMapping("/keyword")
    public ApiResponses<Page<KeywordResponseDto>> findAllkeywords(String keywordName, String keywordDesc, KeywordType keywordType, Pageable pageable) {
        KeywordSearchCondition condition = new KeywordSearchCondition(keywordName, keywordDesc, keywordType);
        return ApiResponses.success(keywordMasterService.findAllKeywordPageDesc(condition, pageable));
    }

}
