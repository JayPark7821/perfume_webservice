package perfume.webservice.perfume.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.auth.common.ApiResponse;
import perfume.webservice.common.exception.CustomBindingException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.perfume.admin.dto.*;
import perfume.webservice.perfume.admin.service.PerfumeAdminService;

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

        if(bindingResult.hasErrors()){
            log.debug("---------------------- SAVE PERFUME BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_PERFUME);
        }
        return ApiResponse.success("data", perfumeAdminService.savePerfume(requestDto));
    }

    @PostMapping("/fragrance")
    public ApiResponse<List<Long>> saveFragrance(@Valid @RequestBody FragranceSaveRequestDtoList requestDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.debug("---------------------- SAVE FRAGRANCE BINDING ERROR --------------------------");
            throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_FRAGRANCE);
        }
        return ApiResponse.success("data", perfumeAdminService.saveFragrance(requestDto));
    }

    @GetMapping("/perfume/all/{page}")
    public ApiResponse<Page<PerfumeResponseDto>>  findAll(@PathVariable int page) {
        return ApiResponse.success("data",perfumeAdminService.findAllPageDesc(page));
    }

}
