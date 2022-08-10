package perfume.webservice.perfume.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perfume.webservice.common.dto.SavedResult;
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ResponseMsgType;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.repository.KeywordRepository;
import perfume.webservice.perfume.domain.dto.response.FragranceResponseDto;
import perfume.webservice.perfume.domain.dto.save.*;
import perfume.webservice.perfume.domain.dto.response.PerfumeResponseDto;
import perfume.webservice.perfume.domain.entity.Fragrance;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.Perfume;
import perfume.webservice.perfume.repository.FragranceGroupRepository;
import perfume.webservice.perfume.repository.FragranceRepository;
import perfume.webservice.perfume.repository.PerfumeQueryRepository;
import perfume.webservice.perfume.repository.PerfumeRepository;
import perfume.webservice.perfume.searchcondition.FragranceSearchCondition;
import perfume.webservice.perfume.searchcondition.PerfumeSearchCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PerfumeAdminService {

	private final PerfumeRepository perfumeRepository;
	private final FragranceRepository fragranceRepository;
	private final FragranceGroupRepository fragranceGroupRepository;
	private final KeywordRepository keywordRepository;
	private final PerfumeQueryRepository perfumeQueryRepository;

	@Transactional
	public SavedResult savePerfume(PerfumeSaveRequestDtoList requestDto) {

		SavedResult savedResult = new SavedResult();

		for (PerfumeSaveRequestDto perfumeSaveRequestDto : requestDto.getPerfumeSaveList()) {

			if (perfumeSaveRequestDto.getId() == null) {

				List<FragranceGroupSaveDto> fragranceGroupDtos = perfumeSaveRequestDto.getFragranceGroup();
				List<Long> fragIds = fragranceGroupDtos.stream().map(FragranceGroupSaveDto::getId)
						.filter(Objects::nonNull)
						.collect(Collectors.toList());

				List<KeywordMaster> keywords = keywordRepository.findByIds(perfumeSaveRequestDto.getKeyword());
				List<Fragrance> fragrances = fragranceRepository.findByIds(fragIds);

				List<FragranceGroup> fragranceGroupList = new ArrayList<>();
//				for (Fragrance fragrance : fragrances) {
//					fragranceGroupList.add(FragranceGroup.builder()
//							.fragrance(fragrance)
//							.perfume(perfume)
//							.containPercentage(fragranceDtos.stream()
//									.filter(f -> fragrance.getId().equals(f.getId()))
//									.filter(f -> f.getPercentage() > 0)
//									.findFirst()
//									.orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.CONTENT_NOT_FOUND,
//											fragrance.getName())).getPercentage())
//							.build());
//				}


//
//				PerfumeSaveDto.builder()
//						.name(perfumeSaveRequestDto.getName())
//						.description(perfumeSaveRequestDto.getDescription())
//						.fragranceGroup();
//				Perfume perfume = perfumeRepository.save(perfumeSaveDto.toEntity());
//				saveFragranceMapping(perfumeSaveDto, perfume, false);
//				savedResult.addInsertedId(perfume.getId());
//
//			} else {
//				Perfume perfume = perfumeRepository.findById(perfumeSaveDto.getId())
//					.orElseThrow(
//						() -> new CustomIllegalArgumentException(ResponseMsgType.PERFUME_NOT_FOUND, perfumeSaveDto.getId()));
////				saveFragranceMapping(perfumeSaveDto, perfume, true);
//				savedResult.addUpdatedId(perfume.getId());
//			}
			}
		}
		return savedResult;
	}

//
//	private void saveFragranceMapping(PerfumeSaveDto perfumeSaveRequestDto, Perfume perfume, boolean isUpdate) {
//		List<FragranceGroupSaveDto> fragranceDtos = perfumeSaveRequestDto.getFragrance();
//		List<Long> fragIds = fragranceDtos.stream().map(FragranceGroupSaveDto::getId)
//			.filter(Objects::nonNull)
//			.collect(Collectors.toList());
//
//
//
//		if (fragranceDtos.size() != fragIds.stream().distinct().count()) {
//			throw new CustomIllegalArgumentException(ResponseMsgType.DUPLI_FRAGRANCE, perfume.getId());
//		}
//		if (fragIds.size() != fragrances.size()) {
//			fragIds.removeAll(fragrances.stream().map(Fragrance::getId).collect(Collectors.toList()));
//			throw new CustomIllegalArgumentException(ResponseMsgType.FRAGRANCE_NOT_FOUND, fragIds);
//		}
//
//		List<FragranceGroup> fragranceGroupList = new ArrayList<>();
//
//		for (Fragrance fragrance : fragrances) {
//			fragranceGroupList.add(FragranceGroup.builder()
//				.fragrance(fragrance)
//				.perfume(perfume)
//				.containPercentage(fragranceDtos.stream()
//					.filter(f -> fragrance.getId().equals(f.getId()))
//					.filter(f -> f.getPercentage() > 0)
//					.findFirst()
//					.orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.CONTENT_NOT_FOUND,
//						fragrance.getName())).getPercentage())
//				.build());
//		}
//		if (isUpdate) {
//			fragranceGroupRepository.deleteAllByPerfumeId(perfume.getId());
//			perfume.updatePerfumeWithoutKeyword(perfumeSaveRequestDto.getName(), perfumeSaveRequestDto.getDescription(), fragranceGroupList);
//		} else {
//			for (FragranceGroup fragranceGroup : fragranceGroupList) {
//				perfume.addFragrance(fragranceGroup);
//			}
//
//		}
//	}

	@Transactional
	public SavedResult saveFragrance(FragranceSaveDtoList requestDto) {
		SavedResult savedResult = new SavedResult();

		for (FragranceSaveDto fragranceSaveDto : requestDto.getFragranceSaveList()) {
			if (fragranceSaveDto.getId() == null) {
				savedResult.addInsertedId(fragranceRepository.save(fragranceSaveDto.toEntity()).getId());
			} else {
				Fragrance fragrance = fragranceRepository.findById(fragranceSaveDto.getId())
					.orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.FRAGRANCE_NOT_FOUND, fragranceSaveDto.getId()));
				fragrance.update(fragranceSaveDto);
				savedResult.addUpdatedId(fragranceSaveDto.getId());
			}
		}
		return savedResult;
	}

	public Page<FragranceResponseDto> findAllFragrancePageDesc(FragranceSearchCondition condition, Pageable pageable) {
		Page<Fragrance> results = perfumeQueryRepository.searchFragrancesWithCondition(condition, pageable);
		return results.map(FragranceResponseDto::new);
	}

	public Page<PerfumeResponseDto> findAllPerfumePageDesc(PerfumeSearchCondition condition, Pageable pageable) {
		Page<Perfume> results = perfumeQueryRepository.searchPerfumesWithCondition(condition, pageable);
		return results.map(PerfumeResponseDto::new);
	}

}
