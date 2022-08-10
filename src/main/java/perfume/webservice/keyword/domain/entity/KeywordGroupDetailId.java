package perfume.webservice.keyword.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
public class KeywordGroupDetailId implements Serializable {

    private Long keywordGroup;
    private Long keywordMaster;

}
