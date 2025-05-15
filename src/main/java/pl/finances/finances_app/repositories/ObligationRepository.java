package pl.finances.finances_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.dto.NearestObligationsDTO;
import pl.finances.finances_app.repositories.entities.ObligationEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObligationRepository extends JpaRepository<ObligationEntity, Long> {
    ObligationEntity save(ObligationEntity transaction);
    boolean existsById(Long id);
    Optional<ObligationEntity> findById(Long id);
    void deleteById(Long id);

    @Query(value = """
    SELECT o.title AS obligationTitle, o.date_to_pay AS dateToPay, o.amount AS obligationAmount
    FROM obligation_entity o
    WHERE o.is_done IS FALSE
    ORDER BY o.date_to_pay ASC
    LIMIT 2
""", nativeQuery = true)
    List<NearestObligationsDTO> getNearest2Obligations(@Param("id") long id);
}
