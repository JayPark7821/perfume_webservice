package perfume.webservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import perfume.webservice.category.domain.entity.Category;
import perfume.webservice.perfume.domain.entity.Fragrance;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.id in :ids")
    List<Category> findByIds(@Param("ids") Collection<Long> ids);


}
