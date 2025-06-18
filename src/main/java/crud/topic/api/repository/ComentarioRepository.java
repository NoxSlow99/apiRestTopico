package crud.topic.api.repository;

import crud.topic.api.model.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {
}
