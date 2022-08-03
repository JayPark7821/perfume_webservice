package perfume.webservice.perfume.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.perfume.common.domain.Fragrance;
import perfume.webservice.perfume.common.domain.Perfume;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    @Builder
    FragranceSaveRequestDto(String name , String desc, List<FragranceGroupSaveDto> fragranceList) {
        this.name = name;
        this.desc = desc;

    }


    public Fragrance toEntity() {
        return Fragrance.builder()
                .fragranceName(name)
                .fragranceDesc(desc)
                .build();
    }

}
