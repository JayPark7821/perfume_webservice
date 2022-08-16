package perfume.webservice.perfume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perfume.webservice.keyword.domain.entity.KeywordGroup;

public interface KeywordGroupRepository extends JpaRepository<KeywordGroup, Long> {
}
