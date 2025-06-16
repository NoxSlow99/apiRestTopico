package crud.topic.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private UserEntity autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id", nullable = false)
    private TopicoEntity topico;
}
