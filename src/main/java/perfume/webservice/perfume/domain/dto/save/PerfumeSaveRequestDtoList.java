package perfume.webservice.perfume.domain.dto.save;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
public class PerfumeSaveRequestDtoList {
    @Valid
    private List<PerfumeSaveRequestDto> perfumeSaveList;

    @Builder
    public PerfumeSaveRequestDtoList(List<PerfumeSaveRequestDto> perfumeSaveList) {
        this.perfumeSaveList = perfumeSaveList;
    }
}
