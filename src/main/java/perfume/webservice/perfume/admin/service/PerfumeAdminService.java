package perfume.webservice.perfume.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.perfume.admin.dto.*;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;
import perfume.webservice.perfume.common.repository.FragranceRepository;
import perfume.webservice.perfume.common.repository.PerfumeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PerfumeAdminService {

    private final PerfumeRepository perfumeRepository;
    private final FragranceRepository fragranceRepository;

    @Transactional
    public List<Long> savePerfume(PerfumeSaveRequestDtoList requestDto) {
        List<Long> ids = new ArrayList<>();
        for (PerfumeSaveRequestDto perfumeSaveRequestDto : requestDto.getPerfumeSaveList()) {
            Perfume perfume = perfumeRepository.save(perfumeSaveRequestDto.toEntity());
            List<FragranceGroupSaveDto> fragranceDtos = perfumeSaveRequestDto.getFragrance();

            List<Long> fragIds = fragranceDtos.stream().map(FragranceGroupSaveDto::getId).collect(Collectors.toList());
            List<Fragrance> fragrances = fragranceRepository.findByNames(fragIds);

            for (Fragrance fragrance : fragrances) {
                perfume.addFragrance(FragranceGroup.builder()
                        .fragrance(fragrance)
                        .perfume(perfume)
                        .containPercentage(fragranceDtos.stream()
                                .filter(f -> fragrance.getId().equals(f.getId()))
                                .findAny()
                                .orElseThrow(() -> new IllegalArgumentException("함유 퍼센트가 없습니다.")).getPercentage())
                        .build());
            }
            ids.add(perfume.getId());
        }
        return ids;
    }


    @Transactional
    public Long saveFragrance(FragranceSaveDto requestDto) {
        return null;
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
