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
import perfume.webservice.perfume.domain.entity.PerfumeKeyword;
import perfume.webservice.perfume.repository.*;
import perfume.webservice.perfume.searchcondition.FragranceSearchCondition;
import perfume.webservice.perfume.searchcondition.PerfumeSearchCondition;

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
	private final PerfumeKeywordRepository perfumeKeywordRepository;
	private final PerfumeQueryRepository perfumeQueryRepository;

	@Transactional
	public SavedResult savePerfume(PerfumeSaveRequestDtoList requestDto) {

		SavedResult savedResult = new SavedResult();

		for (PerfumeSaveRequestDto perfumeSaveRequestDto : requestDto.getPerfumeSaveList()) {

			if (perfumeSaveRequestDto.getId() == null) {
				Perfume perfume = perfumeRepository.save(perfumeSaveRequestDto.toEntity());

				setFragranceAndKeyword(perfumeSaveRequestDto, perfume, false);

				savedResult.addInsertedId(perfume.getId());
			} else {
				Perfume perfume = perfumeRepository.findById(perfumeSaveRequestDto.getId())
						.orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.PERFUME_NOT_FOUND, perfumeSaveRequestDto.getId()));
				setFragranceAndKeyword(perfumeSaveRequestDto, perfume, true);

				savedResult.addUpdatedId(perfume.getId());
			}
		}
		return savedResult;
	}

	private void setFragranceAndKeyword(PerfumeSaveRequestDto perfumeSaveRequestDto, Perfume perfume, Boolean isUpdate) {
		List<FragranceGroupSaveDto> fragranceGroupDtos = perfumeSaveRequestDto.getFragranceGroup();
		List<Long> fragIds = fragranceGroupDtos.stream().map(FragranceGroupSaveDto::getId)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		List<KeywordMaster> keywords = keywordRepository.findByIds(perfumeSaveRequestDto.getKeyword());
		// 중복 키워드
		if (perfumeSaveRequestDto.getKeyword().size() != perfumeSaveRequestDto.getKeyword().stream().distinct().count()) {
			throw new CustomIllegalArgumentException(ResponseMsgType.DUPLI_KEYWORD, perfume.getId());
		}
		// 등록x 키워드
		if (keywords.size() != perfumeSaveRequestDto.getKeyword().size()) {
			throw new CustomIllegalArgumentException(ResponseMsgType.KEYWORD_NOT_FOUND, perfume.getId());
		}
		List<PerfumeKeyword> perfumeKeywordList = keywords.stream()
				.map(keyword -> PerfumeKeyword.builder()
						.perfume(perfume)
						.keywordMaster(keyword)
						.build())
				.collect(Collectors.toList());


		List<Fragrance> fragrances = fragranceRepository.findByIds(fragIds);
		// 중복 향
		if (fragranceGroupDtos.size() != fragIds.stream().distinct().count()) {
			throw new CustomIllegalArgumentException(ResponseMsgType.DUPLI_FRAGRANCE, perfume.getId());
		}
		// 등록x 향
		if (fragIds.size() != fragrances.size()) {
			fragIds.removeAll(fragrances.stream().map(Fragrance::getId).collect(Collectors.toList()));
			throw new CustomIllegalArgumentException(ResponseMsgType.FRAGRANCE_NOT_FOUND, fragIds);
		}

		List<FragranceGroup> fragranceGroupList = fragrances.stream()
				.map(fragrance -> FragranceGroup.builder()
						.fragrance(fragrance)
						.perfume(perfume)
						.containPercentage(fragranceGroupDtos.stream()
								.filter(f -> fragrance.getId().equals(f.getId()))
								.filter(f -> f.getPercentage() > 0)
								.findFirst()
								.orElseThrow(() -> new CustomIllegalArgumentException(ResponseMsgType.CONTAIN_PERCENTAGE_NOT_FOUND,
										fragrance.getName())).getPercentage())
						.build()).collect(Collectors.toList());

		if (isUpdate) {
			perfumeKeywordRepository.deleteAllByPerfumeId(perfume.getId());
			fragranceGroupRepository.deleteAllByPerfumeId(perfume.getId());
			perfume.update(PerfumeSaveDto.builder()
					.fragranceGroup(fragranceGroupList)
					.description(perfumeSaveRequestDto.getDescription())
					.name(perfumeSaveRequestDto.getName())
					.keyword(perfumeKeywordList)
					.build());

		}else{
			perfume.setKeyword(perfumeKeywordList);
			perfume.setFragranceGroup(fragranceGroupList);
		}
	}

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
