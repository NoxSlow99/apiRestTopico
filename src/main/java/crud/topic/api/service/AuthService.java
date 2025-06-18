package crud.topic.api.service;

import crud.topic.api.common.exception.ConflictException;
import crud.topic.api.common.util.JwtUtils;
import crud.topic.api.dto.request.AuthCreateUserRequest;
import crud.topic.api.dto.request.AuthLoginRequest;
import crud.topic.api.dto.response.AuthResponse;
import crud.topic.api.dto.response.LoginResponse;
import crud.topic.api.model.UserEntity;
import crud.topic.api.model.auth.RoleEntity;
import crud.topic.api.repository.RoleRepository;
import crud.topic.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsLoader userDetailsLoader;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse create(AuthCreateUserRequest authCreateUser) {
        String email = authCreateUser.email();
        String username = authCreateUser.username();
        String password = authCreateUser.password();
        UserEntity isEmailExist = userRepository.findByEmailOrUsername(email, username).orElse(null);


        if (isEmailExist != null) {
            throw new ConflictException("Usuario ya registrado.");
        }

        List<String> roleRequest = authCreateUser.roleRequest().roleListName();
        Set<RoleEntity> roleEntityList = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest));

        UserEntity userEntity = UserEntity.builder()
                .name(authCreateUser.name())
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(roleEntityList)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(), null, authorities);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(
                email,
                "User created successfully",
                accessToken,
                true
        );
    }

    public LoginResponse login(AuthLoginRequest loginRequest) {
        String emailOrUsername = loginRequest.emailOrUsername();
        String password = loginRequest.password();

        Authentication authentication = this.authenticate(emailOrUsername, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        UserDetails userDetails = userDetailsLoader.loadUserByUsername(emailOrUsername);

        return new LoginResponse(emailOrUsername, true, userDetails.isEnabled(), accessToken);
    }

    public Authentication authenticate(String emailOrUsername, String password) {
        UserDetails userDetails = userDetailsLoader.loadUserByUsername(emailOrUsername);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(emailOrUsername, password, userDetails.getAuthorities());
    }
}
