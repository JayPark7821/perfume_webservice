package perfume.webservice.perfume.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PerfumeSaveRequestDtoList {
    @Valid
    private List<PerfumeSaveRequestDto> perfumeSaveList;

    @Builder
    PerfumeSaveRequestDtoList(List<PerfumeSaveRequestDto> perfumeSaveList) {
        this.perfumeSaveList = perfumeSaveList;

    }

}
