package perfume.webservice.perfume.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.Perfume;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class FragranceSaveRequestDto {

    private String id;
    @Valid
    @NotNull
    private String name;
    @Valid
    @NotNull
    private String desc;

    public Fragrance toEntity() {
        return Fragrance.builder()
                .fragranceName(name)
                .fragranceDesc(desc)
                .build();
    }

}
