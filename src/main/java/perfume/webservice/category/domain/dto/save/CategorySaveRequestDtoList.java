package perfume.webservice.category.domain.dto.save;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategorySaveRequestDtoList {

    private List<CategorySaveRequestDto> categorySaveRequestDto;

}
