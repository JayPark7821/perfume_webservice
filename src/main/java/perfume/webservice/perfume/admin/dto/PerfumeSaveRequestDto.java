package perfume.webservice.perfume.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    @Builder
    PerfumeSaveRequestDto(String name , String description,List<FragranceGroupSaveDto> fragranceList) {
        this.name = name;
        this.description = description;
        this.fragrance = Objects.requireNonNullElseGet(fragranceList, ArrayList::new);
    }
    public Perfume toEntity() {

        return Perfume.builder()
                .name(name)
                .description(description)
                .build();
    }
}
