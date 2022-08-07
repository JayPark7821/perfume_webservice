package perfume.webservice.perfume.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.perfume.admin.dto.FragranceGroupSaveDto;
import perfume.webservice.perfume.admin.dto.FragranceSaveRequestDto;
import perfume.webservice.perfume.admin.dto.FragranceSaveRequestDtoList;
import perfume.webservice.perfume.admin.dto.PerfumeResponseDto;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDto;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDtoList;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;
import perfume.webservice.perfume.common.repository.FragranceGroupRepository;
import perfume.webservice.perfume.common.repository.FragranceRepository;
import perfume.webservice.perfume.common.repository.PerfumeQueryRepository;
import perfume.webservice.perfume.common.repository.PerfumeRepository;
import perfume.webservice.perfume.common.searchcondition.PerfumeSearchCondition;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PerfumeAdminService {

	private final PerfumeRepository perfumeRepository;
	private final FragranceRepository fragranceRepository;
	private final FragranceGroupRepository fragranceGroupRepository;
	private final PerfumeQueryRepository perfumeQueryRepository;

	@Transactional
	public SavedResult savePerfume(PerfumeSaveRequestDtoList requestDto) {

		List<Long> insertedIds = new ArrayList<>();
		List<Long> updatedIds = new ArrayList<>();

		for (PerfumeSaveRequestDto perfumeSaveRequestDto : requestDto.getPerfumeSaveList()) {
			Long saveRequestId = perfumeSaveRequestDto.getId();

			if (saveRequestId == null) {
				Perfume perfume = perfumeRepository.save(perfumeSaveRequestDto.toEntity());
				saveFragranceMapping(perfumeSaveRequestDto, perfume, false);
				insertedIds.add(perfume.getId());

			} else {
				Perfume perfume = perfumeRepository.findById(saveRequestId)
					.orElseThrow(
						() -> new CustomIllegalArgumentException(ResponseMsgType.PERFUME_NOT_FOUND, saveRequestId));
				saveFragranceMapping(perfumeSaveRequestDto, perfume, true);
				updatedIds.add(perfume.getId());
			}
		}
		return new SavedResult(insertedIds, updatedIds, (updatedIds.size() + insertedIds.size()));
	}

	private void saveFragranceMapping(PerfumeSaveRequestDto perfumeSaveRequestDto, Perfume perfume, boolean isUpdate) {
		List<FragranceGroupSaveDto> fragranceDtos = perfumeSaveRequestDto.getFragrance();
		List<Long> fragIds = fragranceDtos.stream().map(FragranceGroupSaveDto::getId)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		List<Fragrance> fragrances = fragranceRepository.findByIds(fragIds);

		if (fragranceDtos.size() != fragIds.stream().distinct().count()) {
			throw new CustomIllegalArgumentException(ResponseMsgType.DUPLI_FRAGRANCE, perfume.getId());
		}
		if (fragIds.size() != fragrances.size()) {
			fragIds.removeAll(fragrances.stream().map(Fragrance::getId).collect(Collectors.toList()));
			throw new CustomIllegalArgumentException(ResponseMsgType.FRAGRANCE_NOT_FOUND, fragIds);
		}

		List<FragranceGroup> fragranceGroupList = new ArrayList<>();

		for (Fragrance fragrance : fragrances) {
			fragranceGroupList.add(FragranceGroup.builder()
				.fragrance(fragrance)
				.perfume(perfume)
				.containPercentage(fragranceDtos.stream()
					.filter(f -> fragrance.getId().equals(f.getId()))
					.filter(f -> f.getPercentage() > 0)
					.findFirst()
					.orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.CONTENT_NOT_FOUND,
						fragrance.getName())).getPercentage())
				.build());
		}
		if (isUpdate) {
			fragranceGroupRepository.deleteAllByPerfumeId(perfume.getId());
			perfume.update(perfumeSaveRequestDto.getName(), perfumeSaveRequestDto.getDescription(), fragranceGroupList);
		} else {
			for (FragranceGroup fragranceGroup : fragranceGroupList) {
				perfume.addFragrance(fragranceGroup);
			}

		}
	}

	@Transactional
	public SavedResult saveFragrance(FragranceSaveRequestDtoList requestDto) {
		List<Long> insertedIds = new ArrayList<>();
		List<Long> updatedIds = new ArrayList<>();

		for (FragranceSaveRequestDto fragranceSaveRequestDto : requestDto.getFragranceSaveList()) {
			Long saveRequestId = fragranceSaveRequestDto.getId();
			if (saveRequestId == null) {
				Fragrance fragrance = fragranceRepository.save(fragranceSaveRequestDto.toEntity());
				insertedIds.add(fragrance.getId());
			} else {
				Fragrance fragrance = fragranceRepository.findById(saveRequestId)
					.orElseThrow(
						() -> new CustomIllegalArgumentException(ResponseMsgType.PERFUME_NOT_FOUND, saveRequestId));
				fragrance.update(fragranceSaveRequestDto.getName(), fragranceSaveRequestDto.getDesc());
				updatedIds.add(fragrance.getId());
			}
		}

		return new SavedResult(insertedIds, updatedIds, (updatedIds.size() + insertedIds.size()));
	}

	public Page<PerfumeResponseDto> findAllPageDesc(PerfumeSearchCondition condition, Pageable pageable) {
		//        Page<Perfume> results = perfumeQueryRepository.searchPerfumesWithCondition(condition, PageRequest.of(page, 10))
		Page<Perfume> results = perfumeQueryRepository.searchPerfumesWithCondition(condition, pageable);
		return results.map(perfume -> new PerfumeResponseDto(perfume));

		//

	}

}
