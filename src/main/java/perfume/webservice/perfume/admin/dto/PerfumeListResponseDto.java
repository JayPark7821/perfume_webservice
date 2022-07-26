package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import perfume.webservice.perfume.domain.Perfume;

@Getter
public class PerfumeListResponseDto {

    private Long id;
    private String name;
    private String description;

    public PerfumeListResponseDto(Perfume entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
