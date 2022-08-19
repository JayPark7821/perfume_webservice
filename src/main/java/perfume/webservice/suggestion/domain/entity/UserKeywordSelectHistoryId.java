package perfume.webservice.suggestion.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserKeywordSelectHistoryId implements Serializable {

    private Long user;
    private Long keywordMaster;
    private LocalDateTime selectedDate;
    
}