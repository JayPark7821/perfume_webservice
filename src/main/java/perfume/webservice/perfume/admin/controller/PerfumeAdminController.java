package perfume.webservice.perfume.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.auth.common.ApiResponse;
import perfume.webservice.common.exception.CustomBindingException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.perfume.admin.dto.*;
import perfume.webservice.perfume.admin.service.PerfumeAdminService;
import perfume.webservice.perfume.common.searchcondition.PerfumeSearchCondition;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class PerfumeAdminController {

    private final PerfumeAdminService perfumeAdminService;


    @PostMapping("/perfume")
    public ApiResponse<Map<String, Object>> savePerfume(@RequestBody @Valid PerfumeSaveRequestDtoList requestDto, BindingResult bindingResult) {

        // TODO: 2022-08-02 : exception 메시지 처리 & 테스트 검증
        if(bindingResult.hasErrors()){
            log.error("---------------------- SAVE PERFUME BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_PERFUME);
        }else{
            if (requestDto.getPerfumeSaveList() == null) {
                log.error("---------------------- SAVE PERFUME BINDING --------------------------");
                throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_PERFUME);

            }
        }
        return ApiResponse.success("data", perfumeAdminService.savePerfume(requestDto));
    }

    @PostMapping("/fragrance")
    public ApiResponse<List<Long>> saveFragrance(@RequestBody @Valid FragranceSaveRequestDtoList requestDto, BindingResult bindingResult) {
        // TODO: 2022-08-02 : exception 메시지 처리 & 테스트 검증
        if(bindingResult.hasErrors()){
            log.error("---------------------- SAVE FRAGRANCE BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_FRAGRANCE);
        }else{
            if (requestDto.getFragranceSaveList() == null) {
                log.error("---------------------- SAVE FRAGRANCE NULL --------------------------");
                throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_FRAGRANCE);
            }
        }
        return ApiResponse.success("data", perfumeAdminService.saveFragrance(requestDto));
    }

    @GetMapping("/perfume/all")
    public ApiResponse<Page<PerfumeResponseDto>>  findAll(@RequestBody PerfumeSearchCondition condition, Pageable pageable) {
        return ApiResponse.success("data",perfumeAdminService.findAllPageDesc(condition,pageable));
    }

}
