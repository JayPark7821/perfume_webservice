package perfume.webservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.category.domain.entity.CategoryGroup;
import perfume.webservice.category.domain.entity.CategoryGroupDetail;

import java.util.Collection;
import java.util.List;

public interface CategoryGroupDetailRepository extends JpaRepository<CategoryGroupDetail, Long> {



}
