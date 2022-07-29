package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Getter
@NoArgsConstructor
public class FragranceSaveRequestDtoList {
    @Valid
    private List<FragranceSaveRequestDto> fragranceSaveList;
}
