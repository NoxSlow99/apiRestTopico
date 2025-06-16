package crud.topic.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequest(
        @NotBlank
        @Size(max = 100)
        String emailOrUsername,
        @NotBlank
        @Size(min = 8, max = 20, message = "Debe estar entre 8 y 20 caracteres")
        String password
) {}
