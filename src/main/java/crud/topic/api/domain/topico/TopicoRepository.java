package crud.topic.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// ? Clase que guarda los datos recibidos a la base de datos
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByEstatusTrue(Pageable paginacion);
}
