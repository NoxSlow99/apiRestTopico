package crud.topic.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topics")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @Column(unique = true, nullable = false)
    private String mensaje;
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    @Column(nullable = false)
    private Boolean estatus;
    @Column(name = "updated_at")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private UserEntity autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoEntity curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<ComentarioEntity> comentarios;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<VotoEntity> votos;
}
