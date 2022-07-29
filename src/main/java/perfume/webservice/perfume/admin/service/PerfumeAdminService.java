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
import perfume.webservice.common.exception.CustomIllegalArgumentException;
import perfume.webservice.common.exception.ExceptionType;
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
                        .orElseThrow(() -> new CustomIllegalArgumentException(ExceptionType.PERFUME_NOT_FOUND, saveRequestId));
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

        List<Fragrance> fragrances = fragranceRepository.findByIds(fragIds);


        if(fragranceDtos.size() != fragIds.stream().distinct().count()){
            throw new CustomIllegalArgumentException(ExceptionType.DUPLI_FRAGRANCE, perfume.getId());
        }
        if (fragIds.size() != fragrances.size()) {
            fragIds.removeAll(fragrances.stream().map(Fragrance::getId).collect(Collectors.toList()));
            throw new CustomIllegalArgumentException(ExceptionType.FRAGRANCE_NOT_FOUND, fragIds);
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
                            .orElseThrow(() -> new CustomIllegalArgumentException(ExceptionType.CONTENT_NOT_FOUND, fragrance.getFragranceName())).getPercentage())
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
