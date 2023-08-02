package crud.topic.api.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Column(unique = true)
    private String mensaje;
    private Date fecha_creacion;
    private Boolean estatus;
    private String autor;
    private String curso;

    private Date fecha_actualizacion;

    // ! Método @PrePersist para establecer la fecha de creación antes de guardar
    @PrePersist
    protected void onCreate() {
        fecha_creacion = new Date();
    }

    // ! Método @PreUpdate para guardar la fecha cuando se actualiza
    @PreUpdate
    public void preUpdate() {
        fecha_actualizacion = new Date();
    }

    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.estatus = true;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.autor = datosRegistroTopico.autor();
        this.curso = datosRegistroTopico.curso();
    }

    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        if(datosActualizarTopico.titulo() != null){
            this.titulo = datosActualizarTopico.titulo();
        }

        if(datosActualizarTopico.mensaje() != null){
            this.mensaje = datosActualizarTopico.mensaje();
        }

        if(datosActualizarTopico.autor() != null){
            this.autor = datosActualizarTopico.autor();
        }

        if(datosActualizarTopico.curso() != null){
            this.curso = datosActualizarTopico.curso();
        }


    }

    public void desactivarTopico() {
        this.estatus = false;
    }

    // * Dando formato a la fecha de creación
    public String getFecha_creacion() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(fecha_creacion);
    }
}
