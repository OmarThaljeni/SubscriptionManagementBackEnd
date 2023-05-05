package Application.Services;

import Application.Models.*;
import Application.Repository.RoleRepository;
import Application.Security.JwtService;
import Application.Repository.TokenRepository;
import Application.Repository.UserRepository;
import Application.Security.Payload.AuthenticationRequest;
import Application.Security.Payload.AuthenticationResponse;
import Application.Security.Payload.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final RoleRepository roleRepository;
  private final String statusInProgress = "En attente";
  private final String statusAccepted= "Accepté";

  public AuthenticationResponse register(RegisterRequest registerRequest) {
    Set<String> strRoles = registerRequest.getRoles();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    }
    var user = User.builder()
        .firstname(registerRequest.getFirstname())
        .lastname(registerRequest.getLastname())
        .email(registerRequest.getEmail())
        .phone(registerRequest.getPhone())
        .adress(registerRequest.getAdress())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
            .status(statusInProgress)
        .roles(roles)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .id(user.getId())
            .roles(user.getRoles())
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    Set<Role> roleSet = user.getRoles();
    for (Iterator<Role> it = roleSet.iterator(); it.hasNext(); ) {
      Role r = it.next();
      if(r.getId().equals(1)) {
        if(statusAccepted.equals(user.getStatus())) {
          var jwtToken = jwtService.generateToken(user);
          revokeAllUserTokens(user);
          saveUserToken(user, jwtToken);
          return AuthenticationResponse.builder()
                  .token(jwtToken)
                  .id(user.getId())
                  .roles(user.getRoles())
                  .build();
        } else {
          throw new RuntimeException();
        }
      } else {
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .roles(user.getRoles())
                .build();
      }
    }
    return null;
  }

  public void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
