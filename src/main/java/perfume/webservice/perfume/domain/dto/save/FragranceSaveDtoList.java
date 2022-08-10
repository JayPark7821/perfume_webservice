package perfume.webservice.perfume.domain.dto.save;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
public class FragranceSaveDtoList {
    @Valid
    private List<FragranceSaveDto> fragranceSaveList;

    @Builder
    FragranceSaveDtoList(List<FragranceSaveDto> fragranceSaveList) {
        this.fragranceSaveList = fragranceSaveList;

    }

}
