package crud.topic.api.common.mapper;

import crud.topic.api.dto.response.TopicoResponse;
import crud.topic.api.model.TopicoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TopicoMapper {

    //    @Mapping(source = "autor.username", target = "autor")
//    @Mapping(source = "curso.nombre", target = "curso")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "mensaje", target = "mensaje")
    @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    @Mapping(source = "fechaModificacion", target = "fechaModificacion")
    @Mapping(source = "estatus", target = "estatus")
    @Mapping(target = "autor", expression = "java(entity.getAutor().getUsername())")
    @Mapping(target = "curso", expression = "java(entity.getCurso() != null ? entity.getCurso().getNombre() : null)")
    TopicoResponse toResponse(TopicoEntity entity);
}
