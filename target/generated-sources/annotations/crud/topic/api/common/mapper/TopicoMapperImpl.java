package crud.topic.api.common.mapper;

import crud.topic.api.dto.response.TopicoResponse;
import crud.topic.api.model.TopicoEntity;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-17T16:10:33-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class TopicoMapperImpl implements TopicoMapper {

    @Override
    public TopicoResponse toResponse(TopicoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String titulo = null;
        String mensaje = null;
        LocalDateTime fechaCreacion = null;
        LocalDateTime fechaModificacion = null;
        boolean estatus = false;

        id = entity.getId();
        titulo = entity.getTitulo();
        mensaje = entity.getMensaje();
        fechaCreacion = entity.getFechaCreacion();
        fechaModificacion = entity.getFechaModificacion();
        if ( entity.getEstatus() != null ) {
            estatus = entity.getEstatus();
        }

        String autor = entity.getAutor().getUsername();
        String curso = entity.getCurso() != null ? entity.getCurso().getNombre() : null;

        TopicoResponse topicoResponse = new TopicoResponse( id, titulo, mensaje, fechaCreacion, fechaModificacion, estatus, autor, curso );

        return topicoResponse;
    }
}
