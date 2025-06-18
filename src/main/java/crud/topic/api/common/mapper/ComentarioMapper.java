package crud.topic.api.common.mapper;

import crud.topic.api.dto.response.CommentResponse;
import crud.topic.api.model.ComentarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(target = "autor", expression = "java(entity.getAutor().getUsername())")
    @Mapping(target = "fechaCreacion", source = "fechaCreacion")
    @Mapping(target = "updatedAt", source = "fechaModificacion")
    @Mapping(target = "contenido", source = "comentario")
    CommentResponse toResponse(ComentarioEntity comentario);
}
