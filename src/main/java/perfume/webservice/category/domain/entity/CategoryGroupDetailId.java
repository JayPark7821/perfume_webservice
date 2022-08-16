package perfume.webservice.category.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class CategoryGroupDetailId implements Serializable {

    private Long category;
    private Long categoryGroup;

}
