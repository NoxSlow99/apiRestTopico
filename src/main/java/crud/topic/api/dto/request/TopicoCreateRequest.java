package crud.topic.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicoCreateRequest(
        @NotBlank
        @Size(min = 1, max = 100)
        String titulo,
        @NotBlank
        @Size(min = 1, max = 1000)
        String mensaje,
        @Min(1)
        @NotNull
        Long cursoId
) {
}
