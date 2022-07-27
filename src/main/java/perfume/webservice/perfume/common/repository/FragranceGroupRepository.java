package perfume.webservice.perfume.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perfume.webservice.perfume.common.domain.FragranceGroup;

import java.util.List;

public interface FragranceGroupRepository extends JpaRepository<FragranceGroup, Long> {

    @Modifying
    @Query("delete from FragranceGroup f where f.perfume.id = :id")
    void deleteAllByPerfumeId(@Param("id") Long id);

}
