package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.Perfume;

import java.util.List;

@Getter
@NoArgsConstructor
public class PerfumeSaveRequestDto {

    private Long id;
    private String name;
    private String description;

    private List<FragranceGroupSaveDto> fragrance;

    public Perfume toEntity() {

        return Perfume.builder()
                .name(name)
                .description(description)
                .build();
    }
}
