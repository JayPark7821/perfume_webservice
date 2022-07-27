package perfume.webservice.perfume.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.auth.common.ApiResponse;
import perfume.webservice.perfume.admin.dto.FragranceSaveRequestDtoList;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDtoList;
import perfume.webservice.perfume.admin.service.PerfumeAdminService;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDto;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class PerfumeAdminController {

    private final PerfumeAdminService perfumeAdminService;


    @PostMapping("/perfume")
    public ApiResponse savePerfume(@RequestBody PerfumeSaveRequestDtoList requestDto) {
        return ApiResponse.success("data", perfumeAdminService.savePerfume(requestDto));
    }

    @PostMapping("/fragrance")
    public ApiResponse saveFragrance(@RequestBody FragranceSaveRequestDtoList requestDto) {
        return ApiResponse.success("data", perfumeAdminService.saveFragrance(requestDto));
    }


    @PostMapping("/perfume/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody PerfumeSaveRequestDto perfumeSaveRequestDto) {
        return null;
    }

    @GetMapping("/perfume/{id}")
    public ApiResponse findById(@PathVariable Long id) {
        return null; // ApiResponse.success("data",perfumeAdminService.findById(id).or);
    }

    @GetMapping("/perfume/all")
    public ApiResponse findAll() {
        return ApiResponse.success("findAll",perfumeAdminService.findAllDesc());
    }


}
