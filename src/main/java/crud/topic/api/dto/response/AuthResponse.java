package crud.topic.api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "status", "jwt"})
public record AuthResponse(
        String email,
        String message,
        String token,
        Boolean status
) {}
