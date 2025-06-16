package crud.topic.api.service;

import crud.topic.api.model.UserEntity;
import crud.topic.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailOrUsername(emailOrUsername, emailOrUsername).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userEntity.getRoles().stream().flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }
}
