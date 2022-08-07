package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import perfume.webservice.perfume.common.domain.Perfume;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class PerfumeSaveRequestDto {

    private Long id;
    @Valid
    @NotNull
    private String name;
    @Valid
    @NotNull
    private String description;
    @Valid
    @NotNull
    private List<FragranceGroupSaveDto> fragrance;

    public Perfume toEntity() {

        return Perfume.builder()
                .name(name)
                .description(description)
                .build();
    }
}
