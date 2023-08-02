package crud.topic.api.domain.topico;

import java.util.Date;

public record DatosListadoTopico(Long id, String titulo, String mensaje, String fecha_creacion, Boolean estatus_topico,
                                 String autor, String curso) {

    public DatosListadoTopico (Topico topico){

        // ? Enviando los datos que se van a mostrar al listar
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion(),
                topico.getEstatus(),
                topico.getAutor(),
                topico.getCurso());
    }
}
