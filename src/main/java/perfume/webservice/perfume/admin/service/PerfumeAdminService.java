package perfume.webservice.perfume.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.perfume.admin.dto.*;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;
import perfume.webservice.perfume.common.repository.FragranceGroupRepository;
import perfume.webservice.perfume.common.repository.FragranceRepository;
import perfume.webservice.perfume.common.repository.PerfumeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PerfumeAdminService {

    private final PerfumeRepository perfumeRepository;
    private final FragranceRepository fragranceRepository;
    private final FragranceGroupRepository fragranceGroupRepository;
    private final MessageSource msg;


    @Transactional
    public Map<String, Object> savePerfume(PerfumeSaveRequestDtoList requestDto) {

        List<Long> insertedIds = new ArrayList<>();
        List<Long> updatedIds = new ArrayList<>();
        for (PerfumeSaveRequestDto perfumeSaveRequestDto : requestDto.getPerfumeSaveList()) {
            Long saveRequestId = perfumeSaveRequestDto.getId();

            if ( saveRequestId == null) {
                Perfume perfume = perfumeRepository.save(perfumeSaveRequestDto.toEntity());
                saveFragranceMapping(perfumeSaveRequestDto, perfume, false);
                insertedIds.add(perfume.getId());

            } else {
                Perfume perfume = perfumeRepository.findById(saveRequestId)
                        .orElseThrow(() -> new IllegalArgumentException("없는 향수 입니다."));
                saveFragranceMapping(perfumeSaveRequestDto, perfume, true);
                updatedIds.add(perfume.getId());
            }
        }
        return  Map.of("inserted",insertedIds , "updated", updatedIds , "totalCount", (long) (updatedIds.size() + insertedIds.size()));
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


    public Page<PerfumeResponseDto> findAllPageDesc(int page) {
        return perfumeRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")))
                .map(perfume -> new PerfumeResponseDto(perfume));

    }

}
