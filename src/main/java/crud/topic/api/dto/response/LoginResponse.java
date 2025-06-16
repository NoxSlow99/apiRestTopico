package crud.topic.api.dto.response;

public record LoginResponse(
        String email,
        Boolean status,
        Boolean isEnabled,
        String token
) {
}
