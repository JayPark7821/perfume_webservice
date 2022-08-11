package perfume.webservice.perfume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.perfume.domain.entity.PerfumeKeyword;

public interface PerfumeKeywordRepository extends JpaRepository<PerfumeKeyword, Long> {

    @Modifying
    @Query("delete from PerfumeKeyword f where f.perfume.id = :id")
    void deleteAllByPerfumeId(@Param("id") Long id);

}
