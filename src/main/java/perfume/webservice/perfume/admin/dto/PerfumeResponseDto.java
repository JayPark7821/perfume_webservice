package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PerfumeResponseDto {

    private Long id;
    private String name;
    private String description;

    private List<FragranceGroupResponseDto> fragranceGroupResponseDtoList = new ArrayList<>();

    public void addFragranceGroupResponseDto(FragranceGroupResponseDto fragrance) {
        this.fragranceGroupResponseDtoList.add(fragrance);
    }

    public PerfumeResponseDto(Perfume entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        if (entity.getFragranceGroup().size() == 0) {
            this.fragranceGroupResponseDtoList = new ArrayList<>();
        } else{
            for (FragranceGroup fragrance : entity.getFragranceGroup()) {
                this.addFragranceGroupResponseDto(new FragranceGroupResponseDto(fragrance));
            }
        }

    }
}