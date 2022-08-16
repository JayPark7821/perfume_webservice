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
import perfume.webservice.keyword.domain.dto.save.KeywordGroupSaveRequestDtoList;
import perfume.webservice.keyword.domain.dto.save.KeywordSaveRequestDtoList;
import perfume.webservice.keyword.domain.entity.KeywordType;
import perfume.webservice.keyword.searchcondition.KeywordSearchCondition;
import perfume.webservice.keyword.service.KeywordGroupService;
import perfume.webservice.keyword.service.KeywordMasterService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "KeywordGroupController", description = "키워드 그룹 관련 정보 등록 및 조회 (관리자)")
public class KeywordGroupController {

    private final KeywordGroupService keywordGroupService;

    @Operation(summary = "키워드 그룹 정보 저장(insert & update)", description = "키워드 그룹 정보 저장처리 <br> <br>  insert와 update, 단 건과 다 건 모두 /api/admin/keywordgroup 에서 처리한다.<br> insert와 update의 기준은 id값이며 id가 있으면 update 없으면 insert 한다")
    @PostMapping("/keywordgroup")
    public ApiResponses<SavedResult> saveKeywordGroup(@RequestBody @Valid KeywordGroupSaveRequestDtoList requestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || requestDto.getKeywordGroupList() == null || requestDto.getKeywordGroupList().size() == 0 ) {
            log.error("---------------------- SAVE KEYWORD BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_KEYWORDGROUP);
        }
        return ApiResponses.success(keywordGroupService.saveKeywordGroup(requestDto));
    }


}
