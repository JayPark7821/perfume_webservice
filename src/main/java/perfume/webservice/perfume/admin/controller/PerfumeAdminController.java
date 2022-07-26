package perfume.webservice.perfume.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import perfume.webservice.auth.common.ApiResponse;
import perfume.webservice.perfume.admin.service.PerfumeAdminService;
import perfume.webservice.perfume.dto.PerfumeSaveRequestDto;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class PerfumeAdminController {

    private final PerfumeAdminService perfumeAdminService;

    @PostMapping("/reg/perfume")
    public ApiResponse save(@RequestBody PerfumeSaveRequestDto perfumeSaveRequestDto) {
        return ApiResponse.success("save", perfumeAdminService.save(perfumeSaveRequestDto));
    }

    @GetMapping("/reg/perfume")
    public ApiResponse findAll() {
        return ApiResponse.success("findAll",perfumeAdminService.findAllDesc());
    }

}
