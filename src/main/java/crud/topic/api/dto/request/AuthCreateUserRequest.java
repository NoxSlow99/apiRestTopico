package crud.topic.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthCreateUserRequest(
        @NotBlank
        @Size(max = 100)
        String name,
        @NotBlank
        @Size(max = 20)
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, max = 20, message = "Debe estar entre 8 y 20 caracteres")
        String password,
        @Valid
        AuthCreateRoleRequest roleRequest
) {}
