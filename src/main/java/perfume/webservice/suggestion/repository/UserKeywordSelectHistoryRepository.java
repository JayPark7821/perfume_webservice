package perfume.webservice.suggestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perfume.webservice.suggestion.domain.entity.UserKeywordSelectHistory;
import perfume.webservice.suggestion.domain.entity.UserKeywordSelectHistoryId;

public interface UserKeywordSelectHistoryRepository extends JpaRepository<UserKeywordSelectHistory, UserKeywordSelectHistoryId> {


}
