package perfume.webservice.category.domain.dto.response;


import lombok.Getter;
import perfume.webservice.category.domain.entity.CategoryGroup;

@Getter
public class BaseCategoryGroupResponseDto {

    private Long id;
    private String name;
    private String desc;


    public BaseCategoryGroupResponseDto(CategoryGroup entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.desc = entity.getDesc();
    }
}
