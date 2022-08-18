package perfume.webservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.category.domain.entity.CategoryGroup;

import java.util.List;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {

    @Query("select cg from CategoryGroup cg inner join cg.categoryGroupDetail cgd where cgd.level = 0")
    List<CategoryGroup> findBaseCategoryGroupAll();
}
