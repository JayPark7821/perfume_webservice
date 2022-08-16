package perfume.webservice.perfume.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.perfume.domain.dto.response.PerfumeResponseDto;
import perfume.webservice.perfume.domain.entity.Perfume;
import perfume.webservice.perfume.repository.PerfumeQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerfumeFinderService {

    private final PerfumeQueryRepository perfumeQueryRepository;

    public List<PerfumeResponseDto> searchPerfumesWithKeywords(List<Long> keywords) {
        List<Perfume> perfumes = perfumeQueryRepository.searchPerfumesWithKeywords(keywords);
        return perfumes.stream().map(PerfumeResponseDto::new).collect(Collectors.toList());
    }

}
