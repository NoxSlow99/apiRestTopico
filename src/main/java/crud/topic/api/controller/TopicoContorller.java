package crud.topic.api.controller;

import crud.topic.api.dto.request.TopicoCreateRequest;
import crud.topic.api.dto.request.TopicoUpdRequest;
import crud.topic.api.dto.response.PaginatedResponde;
import crud.topic.api.dto.response.TopicoResponse;
import crud.topic.api.service.TopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
public class TopicoContorller {

    private final TopicService topicoService;

    @PostMapping("/create")
    public ResponseEntity<TopicoResponse> createTopic(@RequestBody @Valid TopicoCreateRequest request) {
        return new ResponseEntity<>(
                this.topicoService.crearTopico(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> getAllTopics(@PathVariable Long id) {
        return new ResponseEntity<>(
                this.topicoService.obtenerTopicoById(id),
                HttpStatus.OK
        );
    }

    @GetMapping
    public PaginatedResponde<TopicoResponse> listTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "true") boolean desc
    ) {
        return topicoService.listarTopicos(page, size, sortBy, desc);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> updateTopic(
            @PathVariable Long id,
            @RequestBody TopicoUpdRequest request
    ) throws AccessDeniedException {
        return new ResponseEntity<>(
                this.topicoService.editarTopico(id, request),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) throws AccessDeniedException {
        this.topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
