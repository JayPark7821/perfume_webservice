package perfume.webservice.perfume.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.perfume.admin.dto.*;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;
import perfume.webservice.perfume.common.repository.FragranceGroupRepository;
import perfume.webservice.perfume.common.repository.FragranceRepository;
import perfume.webservice.perfume.common.repository.PerfumeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PerfumeAdminService {

    private final PerfumeRepository perfumeRepository;
    private final FragranceRepository fragranceRepository;
    private final FragranceGroupRepository fragranceGroupRepository;

    @Transactional
    public List<Long> savePerfume(PerfumeSaveRequestDtoList requestDto) {
        List<Long> ids = new ArrayList<>();
        for (PerfumeSaveRequestDto perfumeSaveRequestDto : requestDto.getPerfumeSaveList()) {
            Long saveRequestId = perfumeSaveRequestDto.getId();

            if ( saveRequestId == null) {
                Perfume perfume = perfumeRepository.save(perfumeSaveRequestDto.toEntity());
                saveFragranceMapping(perfumeSaveRequestDto, perfume, false);
                ids.add(perfume.getId());
            } else {
                Perfume perfume = perfumeRepository.findById(saveRequestId)
                        .orElseThrow(() -> new IllegalArgumentException("없는 향수 입니다."));
                saveFragranceMapping(perfumeSaveRequestDto, perfume, true);
            }
        }
        return ids;
    }

    private void saveFragranceMapping(PerfumeSaveRequestDto perfumeSaveRequestDto, Perfume perfume, boolean isUpdate) {
        List<FragranceGroupSaveDto> fragranceDtos = perfumeSaveRequestDto.getFragrance();
        List<Long> fragIds = fragranceDtos.stream().map(FragranceGroupSaveDto::getId)
                                                    .filter(Objects::nonNull)
                                                    .collect(Collectors.toList());

        List<Fragrance> fragrances = fragranceRepository.findByNames(fragIds);

        if (fragIds.size() != fragrances.size()) {
            throw new IllegalArgumentException("없는 향입니다.");
        }

        List<FragranceGroup> fragranceGroupList = new ArrayList<>();

        for (Fragrance fragrance : fragrances) {
            fragranceGroupList.add(FragranceGroup.builder()
                    .fragrance(fragrance)
                    .perfume(perfume)
                    .containPercentage(fragranceDtos.stream()
                            .filter(f -> fragrance.getId().equals(f.getId()))
                            .findAny()
                            .orElseThrow(() -> new IllegalArgumentException("함유 퍼센트가 없습니다.")).getPercentage())
                    .build());
        }
        if (isUpdate) {
            fragranceGroupRepository.deleteAllByPerfumeId(perfume.getId());
            perfume.update(perfumeSaveRequestDto.getName(), perfumeSaveRequestDto.getDescription(),fragranceGroupList );
        }else{
            for (FragranceGroup fragranceGroup : fragranceGroupList) {
                perfume.addFragrance(fragranceGroup);
            }

        }
    }

    @Transactional
    public List<Long> saveFragrance(FragranceSaveRequestDtoList requestDto) {
        List<Long> ids = new ArrayList<>();
        for (FragranceSaveRequestDto fragranceSaveRequestDto : requestDto.getFragranceSaveList()) {
            Fragrance fragrance = fragranceRepository.save(fragranceSaveRequestDto.toEntity());
            ids.add(fragrance.getId());
        }
        return ids;
    }



    @Transactional(readOnly = true)
    public List<PerfumeResponseDto> findAllDesc() {
        return perfumeRepository.findAllDesc().stream()
                .map(PerfumeResponseDto::new)
                .collect(Collectors.toList());
    }


//    public Object findById(Long id) {
//    }
}
