package perfume.webservice.perfume.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class PerfumeKeywordId implements Serializable{

    private Long perfume;
    private Long keywordMaster;
}
