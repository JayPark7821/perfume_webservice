package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import perfume.webservice.perfume.common.domain.Perfume;

@Getter
public class PerfumeResponseDto {

    private Long id;
    private String name;
    private String description;

    public PerfumeResponseDto(Perfume entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
