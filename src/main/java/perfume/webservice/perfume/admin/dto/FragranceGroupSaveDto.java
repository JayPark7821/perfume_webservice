package perfume.webservice.perfume.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class FragranceGroupSaveDto {
    @Valid
    @NotNull
    private Long id;
    @Valid
    @NotNull
    private int percentage;

    @Builder
    FragranceGroupSaveDto(Long id , int percentage) {
        this.id = id;
        this.percentage = percentage;

    }


}
