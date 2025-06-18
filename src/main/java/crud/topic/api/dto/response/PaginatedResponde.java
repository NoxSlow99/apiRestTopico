package crud.topic.api.dto.response;

import java.util.List;

public record PaginatedResponde<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
}
