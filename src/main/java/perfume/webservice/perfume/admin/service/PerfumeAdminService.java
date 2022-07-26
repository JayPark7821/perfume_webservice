package perfume.webservice.perfume.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.perfume.domain.PerfumeRepository;
import perfume.webservice.perfume.admin.dto.PerfumeListResponseDto;
import perfume.webservice.perfume.admin.dto.PerfumeSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PerfumeAdminService {

    private final PerfumeRepository perfumeRepository;
    @Transactional
    public Long save(PerfumeSaveRequestDto requestDto) {
        return perfumeRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<PerfumeListResponseDto> findAllDesc() {
        return perfumeRepository.findAllDesc().stream()
                .map(PerfumeListResponseDto::new)
                .collect(Collectors.toList());
    }

}
