package perfume.webservice.perfume.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import perfume.webservice.perfume.domain.entity.Fragrance;

@Getter
public class FragranceResponseDto {

    @Schema(description = "향 id", example = "125")
    private Long id;

    @Schema(description = "향 명", example = "woody")
    private String name;

    @Schema(description = "향 설명", example = "soooooo wooody~")
    private String description;


    public FragranceResponseDto(Fragrance entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();

    }
}
