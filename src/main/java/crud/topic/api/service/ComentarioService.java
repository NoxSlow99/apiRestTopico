package crud.topic.api.service;

import crud.topic.api.common.mapper.ComentarioMapper;
import crud.topic.api.dto.request.CommentCreateRequest;
import crud.topic.api.dto.response.CommentResponse;
import crud.topic.api.model.ComentarioEntity;
import crud.topic.api.model.TopicoEntity;
import crud.topic.api.model.UserEntity;
import crud.topic.api.repository.ComentarioRepository;
import crud.topic.api.repository.TopicoRepository;
import crud.topic.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final UserRepository userRepository;
    private final TopicoRepository topicoRepository;
    private final ComentarioMapper comentarioMapper;

    @Transactional
    public CommentResponse createComment(CommentCreateRequest request) {
        String emailOrUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity autor = userRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        TopicoEntity topico = topicoRepository.findById(request.idTopic())
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado"));

        ComentarioEntity comentario = ComentarioEntity.builder()
                .contenido(request.content())
                .fechaCreacion(LocalDateTime.now())
                .topico(topico)
                .autor(autor)
                .build();

        comentarioRepository.save(comentario);

        return comentarioMapper.toResponse(comentario);
    }
}
