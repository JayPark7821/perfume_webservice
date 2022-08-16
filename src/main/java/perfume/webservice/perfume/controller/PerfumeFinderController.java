package perfume.webservice.perfume.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.common.dto.ApiResponses;
import perfume.webservice.perfume.domain.dto.response.PerfumeResponseDto;
import perfume.webservice.perfume.searchcondition.PerfumeSuggestionCondition;
import perfume.webservice.perfume.service.PerfumeFinderService;

import java.util.List;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class PerfumeFinderController {

    private final PerfumeFinderService perfumeFinderService;
    @GetMapping("/findperfume")
    public ApiResponses<List<PerfumeResponseDto>> findPerfumeByKeywords(@RequestBody PerfumeSuggestionCondition condition) {
        return ApiResponses.success(perfumeFinderService.searchPerfumesWithKeywords(condition.getKeywords()));
    }

}
