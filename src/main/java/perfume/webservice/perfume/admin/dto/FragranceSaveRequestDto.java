package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.Perfume;

@Getter
@NoArgsConstructor
public class FragranceSaveRequestDto {

    private String id;
    private String name;
    private String desc;

    public Fragrance toEntity() {
        return Fragrance.builder()
                .fragranceName(name)
                .fragranceDesc(desc)
                .build();
    }

}
