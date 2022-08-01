package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class FragranceGroupSaveDto {
    @Valid
    @NotNull
    private Long id;
    @Valid
    @NotNull
    private int percentage;

}
