package perfume.webservice.perfume.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perfume.webservice.perfume.common.domain.FragranceGroup;

public interface FragranceGroupRepository extends JpaRepository<FragranceGroup, Long> {
}
