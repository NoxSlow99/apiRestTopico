package crud.topic.api.dto.response;

public record LoginResponse(
        String credential,
        Boolean status,
        Boolean isEnabled,
        String token
) {
}
