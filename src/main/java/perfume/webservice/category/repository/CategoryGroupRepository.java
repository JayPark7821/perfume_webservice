package perfume.webservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import perfume.webservice.category.domain.entity.CategoryGroup;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {


}
