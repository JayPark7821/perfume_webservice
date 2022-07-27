package perfume.webservice.perfume.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.perfume.common.domain.Fragrance;

import java.util.Collection;
import java.util.List;

public interface FragranceRepository extends JpaRepository<Fragrance, Long> {

    @Query("select f from Fragrance f where f.id in :ids")
    List<Fragrance> findByNames(@Param("ids") Collection<Long> ids);


}
