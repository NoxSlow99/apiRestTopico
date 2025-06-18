package crud.topic.api.controller;

import crud.topic.api.dto.request.CommentCreateRequest;
import crud.topic.api.dto.response.CommentResponse;
import crud.topic.api.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/comments"))
@RequiredArgsConstructor
public class CometarioController {
    private final ComentarioService comentarioService;

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentCreateRequest request) {
        return new ResponseEntity<>(
                this.comentarioService.createComment(request),
                HttpStatus.CREATED
        );
    }
}
