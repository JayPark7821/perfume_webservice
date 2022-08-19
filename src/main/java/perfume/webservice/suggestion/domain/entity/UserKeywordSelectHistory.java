package perfume.webservice.suggestion.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.user.api.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@IdClass(UserKeywordSelectHistoryId.class)
public class UserKeywordSelectHistory {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private KeywordMaster keywordMaster;

    @Id
    private LocalDateTime selectedDate;

    @Builder
    public UserKeywordSelectHistory(User user, KeywordMaster keywordMaster, LocalDateTime selectedDate) {
        this.user = user;
        this.keywordMaster = keywordMaster;
        this.selectedDate = selectedDate;
    }
}