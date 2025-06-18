package crud.topic.api.repository;

import crud.topic.api.model.TopicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<TopicoEntity, Long> {
    Optional<TopicoEntity> findAllByEstatusIsTrue();

    Page<TopicoEntity> findAllByEstatusIsTrue(Pageable pageable);

    Page<TopicoEntity> findByAutorUsername(String username, Pageable pageable);
}
