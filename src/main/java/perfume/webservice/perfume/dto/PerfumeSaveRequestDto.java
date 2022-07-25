package perfume.webservice.perfume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.domain.Perfume;

@Getter
@NoArgsConstructor
public class PerfumeSaveRequestDto {

    private String name;
    private String description;


    public Perfume toEntity() {
        return Perfume.builder()
                .name(name)
                .description(description)
                .build();
    }
}
