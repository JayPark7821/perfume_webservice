package perfume.webservice.perfume.admin.controller;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perfume.webservice.common.dto.ApiResponses;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomBindingException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.perfume.admin.dto.FragranceSaveRequestDtoList;
import perfume.webservice.perfume.admin.dto.PerfumeResponseDto;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDtoList;
import perfume.webservice.perfume.admin.service.PerfumeAdminService;
import perfume.webservice.perfume.common.searchcondition.PerfumeSearchCondition;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "PerfumeAdminController", description = "향수 관련 정보 등록 및 조회 (관리자)")
public class PerfumeAdminController {

	private final PerfumeAdminService perfumeAdminService;

	@Operation(summary = "향수 정보 저장(insert & update)", description = "향수 정보 저장처리 <br> <br>  insert와 update, 단 건과 다 건 모두 /api/admin/perfume 에서 처리한다.<br> insert와 update의 기준은 id값이며 id가 있으면 update 없으면 insert 한다 <br> <br> 향수의 이름은 중복될 수 없다.")
	@PostMapping("/perfume")
	public ApiResponses<SavedResult> savePerfume(@RequestBody @Valid PerfumeSaveRequestDtoList requestDto,
		BindingResult bindingResult) {

		// TODO: 2022-08-02 : exception 메시지 처리 & 테스트 검증
		if (bindingResult.hasErrors()) {
			log.error("---------------------- SAVE PERFUME BINDING ERROR --------------------------");
			throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_PERFUME);
		} else {
			if (requestDto.getPerfumeSaveList() == null) {
				log.error("---------------------- SAVE PERFUME BINDING --------------------------");
				throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_PERFUME);

			}
		}
		return ApiResponses.success(perfumeAdminService.savePerfume(requestDto));
	}

	@Operation(summary = "향 정보 저장(insert & update)", description = "향 정보 저장처리 <br> <br> insert와 update, 단 건과 다 건 모두 /api/admin/fragrance 에서 처리한다.<br> insert와 update의 기준은 id값이며 id가 있으면 update 없으면 insert 한다  <br> <br> 향의 명칭은 중복될 수 없다.")
	@PostMapping("/fragrance")
	public ApiResponses<SavedResult> saveFragrance(@RequestBody @Valid FragranceSaveRequestDtoList requestDto,
		BindingResult bindingResult) {
		// TODO: 2022-08-02 : exception 메시지 처리 & 테스트 검증
		if (bindingResult.hasErrors()) {
			log.error("---------------------- SAVE FRAGRANCE BINDING ERROR --------------------------");
			throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_FRAGRANCE);
		} else {
			if (requestDto.getFragranceSaveList() == null) {
				log.error("---------------------- SAVE FRAGRANCE NULL --------------------------");
				throw new CustomBindingException(ResponseMsgType.BINDING_ERROR_FRAGRANCE);
			}
		}
		return ApiResponses.success(perfumeAdminService.saveFragrance(requestDto));
	}

	@Operation(summary = "향수 정보 조회 (pageable)", description = "향수 정보 조회 <br> <br>1. 검색 조건 <br>  - 향수명 : perfumeName <br> - 향명: fragranceName)  <br> <br> 2. 페이징 <br>  - 페이지당 출력할 데이터 건수 : size <br> - 조회할 페이지: page")
	@GetMapping("/perfume/all")
	public ApiResponses<Page<PerfumeResponseDto>> findAll(
		@Parameter(in = ParameterIn.QUERY, description = "검색 조건 <br> 검색 조건 (향수명 : perfumeName , 향명: fragranceName) 쿼리 파라미터에 담아 요청, 검색 조건이 없으면 전체 검색 <br> 페이징 처리  <br> 1페이지의 출력할 데이터 건수 : size , 조회할 페이지: page 를 쿼리 파라미터에 담아 요청, 없으면 전체 검색 ")
		// @ModelAttribute PerfumeSearchCondition condition,
		String perfumeName, String fragranceName,
		@ParameterObject @PageableDefault(size = 5, page = 0) Pageable pageable) {

		PerfumeSearchCondition condition = new PerfumeSearchCondition(perfumeName, fragranceName);
		System.out.println("condition = " + condition);
		System.out.println("pageable = " + pageable);
		return ApiResponses.success(perfumeAdminService.findAllPageDesc(condition, pageable));
	}

}
