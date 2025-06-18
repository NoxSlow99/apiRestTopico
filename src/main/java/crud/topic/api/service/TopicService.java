package crud.topic.api.service;

import crud.topic.api.common.mapper.TopicoMapper;
import crud.topic.api.common.util.Utils;
import crud.topic.api.dto.request.TopicoCreateRequest;
import crud.topic.api.dto.request.TopicoUpdRequest;
import crud.topic.api.dto.response.PaginatedResponde;
import crud.topic.api.dto.response.TopicoResponse;
import crud.topic.api.model.CursoEntity;
import crud.topic.api.model.TopicoEntity;
import crud.topic.api.model.UserEntity;
import crud.topic.api.model.auth.RoleEnum;
import crud.topic.api.repository.CursoRepository;
import crud.topic.api.repository.TopicoRepository;
import crud.topic.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final UserRepository userRepository;
    private final TopicoMapper topicoMapper;
    private final Utils utils;

    @Transactional
    public TopicoResponse crearTopico(TopicoCreateRequest request) {
        String emailOrUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity autor = userRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        CursoEntity curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        TopicoEntity topico = TopicoEntity.builder()
                .titulo(request.titulo())
                .mensaje(request.mensaje())
                .fechaCreacion(LocalDateTime.now())
                .estatus(true)
                .autor(autor)
                .curso(curso)
                .build();

        topicoRepository.save(topico);

        return topicoMapper.toResponse(topico);
    }

    public PaginatedResponde<TopicoResponse> listarTopicos(int page, int size, String sortBy, boolean desc) {
        Set<String> allowedSortFields = Set.of("id", "titulo", "mensaje", "fechaCreacion", "estatus");
        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Campo de ordenamiento inválido");
        }

        Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<TopicoEntity> pageTopicos = topicoRepository.findAll(pageable);

        List<TopicoResponse> topicos = pageTopicos.stream()
                .map(topicoMapper::toResponse)
                .toList();

        return new PaginatedResponde<>(
                topicos,
                pageTopicos.getNumber(),
                pageTopicos.getSize(),
                pageTopicos.getTotalElements(),
                pageTopicos.getTotalPages(),
                pageTopicos.isLast()
        );
    }

    public TopicoResponse obtenerTopicoById(Long id) {
        TopicoEntity topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado"));

        return topicoMapper.toResponse(topico);
    }

    @Transactional
    public TopicoResponse editarTopico(Long id, TopicoUpdRequest request) throws AccessDeniedException {
        TopicoEntity topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado"));

        UserEntity currentUser = utils.getAuthenticatedUser();

        boolean isAutor = topico.getAutor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleEnum().equals(RoleEnum.ADMIN));

        if (!isAutor && !isAdmin) {
            throw new AccessDeniedException("No tiene permitido modificar este topico");
        }

        if (request.titulo() != null) {
            topico.setTitulo(request.titulo());
        }

        if (request.mensaje() != null) {
            topico.setMensaje(request.mensaje());
        }

        return topicoMapper.toResponse(topico);
    }

    @Transactional
    public void eliminarTopico(Long id) throws AccessDeniedException {
        TopicoEntity topico = topicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado"));

        UserEntity currentUser = utils.getAuthenticatedUser();

        boolean isAutor = topico.getAutor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getRoleEnum().equals(RoleEnum.ADMIN));

        if (!isAutor && !isAdmin) {
            throw new AccessDeniedException("No tiene permitido eliminar este topico");
        }

        topico.setEstatus(false);
    }
}
