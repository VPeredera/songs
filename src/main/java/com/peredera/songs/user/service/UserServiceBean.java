package com.peredera.songs.user.service;

import com.peredera.songs.token.Token;
import com.peredera.songs.token.TokenRepository;
import com.peredera.songs.token.TokenType;
import com.peredera.songs.user.dto.AuthenticationRequest;
import com.peredera.songs.user.dto.AuthenticationResponse;
import com.peredera.songs.user.dto.RegisterRequest;
import com.peredera.songs.user.domain.Role;
import com.peredera.songs.user.domain.User;
import com.peredera.songs.user.repository.UserRepository;
import com.peredera.songs.util.config.security.JwtService;
import com.peredera.songs.util.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceBean implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        checkIfUserExist(request.getEmail());
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void checkIfUserExist(String email) {
        if(userRepository.findByEmail(email).isPresent()) throw new UserAlreadyExistException();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(!validUserTokens.isEmpty()) {
            validUserTokens.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
