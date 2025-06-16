package crud.topic.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "votos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "topico_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean positivo;

    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private TopicoEntity topico;
}
