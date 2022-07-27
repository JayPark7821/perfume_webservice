package perfume.webservice.perfume.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class FragranceGroupID implements Serializable {

    private static final long serialVersionUID = -2314369197054375513L;
    private Long perfume;

    private Long fragrance;



}
