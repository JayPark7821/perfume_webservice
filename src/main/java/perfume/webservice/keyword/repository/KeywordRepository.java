package perfume.webservice.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.perfume.domain.entity.Fragrance;

import java.util.Collection;
import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordMaster, Long> {

    @Query("select k from KeywordMaster k where k.id in :ids")
    List<KeywordMaster> findByIds(@Param("ids") Collection<Long> ids);

}
