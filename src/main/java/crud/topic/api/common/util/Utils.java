package crud.topic.api.common.util;

import crud.topic.api.model.UserEntity;
import crud.topic.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utils {
    private final UserRepository userRepository;

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String credential = authentication.getName();
        return userRepository.findByEmailOrUsername(credential, credential)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
