package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.FragranceGroup;
import perfume.webservice.perfume.common.domain.Perfume;

@Getter
@NoArgsConstructor
public class FragranceGroupResponseDto {

    private Long fragranceId;
    private String fragranceName;
    private String fragranceDesc;
    private int containPercentage;

    public FragranceGroupResponseDto(FragranceGroup entity) {
        this.fragranceId = entity.getFragrance().getId();
        this.fragranceName = entity.getFragrance().getFragranceName();
        this.fragranceDesc = entity.getFragrance().getFragranceDesc();
        this.containPercentage = entity.getContainPercentage();
    }
}