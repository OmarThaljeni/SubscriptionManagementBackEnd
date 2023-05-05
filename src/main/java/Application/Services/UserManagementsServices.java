package Application.Services;

import Application.Models.ERole;
import Application.Models.Role;
import Application.Models.User;
import Application.Repository.RoleRepository;
import Application.Repository.UserRepository;
import Application.Security.JwtService;
import Application.Security.Payload.UserRequest;
import Application.Security.Payload.AuthenticationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManagementsServices {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final String statusInProgress = "En attente";
    private final String statusAccepted= "Accepté";

    public Collection<User> getUserResponsable() {
        Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        return userRepository.findByRoles(responsableRole);
    }

    public Collection<User> getUserCustomer() {
        Role responsableRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        return userRepository.findByRoles(responsableRole);
    }

    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AuthenticationResponse addCustomer(@RequestBody UserRequest userRequest) {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        var user = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .adress(userRequest.getAdress())
                .status(statusInProgress)
                .password(passwordEncoder.encode(generatePassword(userRequest.getFirstname(), userRequest.getLastname())))
                .roles(roles)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        authenticationService.saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .roles(user.getRoles())
                .build();
    }

    public AuthenticationResponse updateCustomer(@PathVariable("id") Integer id,@RequestBody UserRequest userRequest) {
        Optional<User> userData = userRepository.findById(id);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        if(userData.isPresent()) {
            User U = userData.get();
            U.setFirstname(userRequest.getFirstname());
            U.setLastname(userRequest.getLastname());
            U.setAdress(userRequest.getAdress());
            U.setEmail(userRequest.getEmail());
            U.setPhone(userRequest.getPhone());
            U.setPassword(passwordEncoder.encode(generatePassword(userRequest.getFirstname(), userRequest.getLastname())));
            U.setRoles(roles);
            var savedUser = userRepository.save(U);
            var jwtToken = jwtService.generateToken(U);
            authenticationService.saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .id(U.getId())
                    .roles(U.getRoles())
                    .build();
        } else return null;
    }

    private  String generatePassword(String firstName, String lastName) {
    Date d=new Date();
    int year =d.getYear()+1900;
    String firstLetterFromFirstName = String.valueOf(firstName.charAt(0)).toUpperCase();
    String firstLetterFromLastName = String.valueOf(lastName.charAt(0)).toUpperCase();
    //MaramCherif@2023
    return firstLetterFromFirstName +
            firstName.substring(1) +
            firstLetterFromLastName +
            lastName.substring(1) +
            "@" +
            year;
}

    public AuthenticationResponse addResponsable(@RequestBody UserRequest userRequest) {
        Set<Role> roles = new HashSet<>();
        Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(responsableRole);
        var user = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .adress(userRequest.getAdress())
                .password(passwordEncoder.encode(generatePassword(userRequest.getFirstname(), userRequest.getLastname())))
                .roles(roles)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        authenticationService.saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .roles(user.getRoles())
                .build();
    }

    public AuthenticationResponse updateResponsable(@PathVariable("id") Integer id,@RequestBody UserRequest userRequest) {
        Optional<User> userData = userRepository.findById(id);
        Set<Role> roles = new HashSet<>();
        Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(responsableRole);
        if(userData.isPresent()) {
            User U = userData.get();
            U.setFirstname(userRequest.getFirstname());
            U.setLastname(userRequest.getLastname());
            U.setAdress(userRequest.getAdress());
            U.setEmail(userRequest.getEmail());
            U.setPhone(userRequest.getPhone());
            U.setPassword(passwordEncoder.encode(generatePassword(userRequest.getFirstname(), userRequest.getLastname())));
            U.setRoles(roles);
            var savedUser = userRepository.save(U);
            var jwtToken = jwtService.generateToken(U);
            authenticationService.saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .id(U.getId())
                    .roles(U.getRoles())
                    .build();
        } else return null;
    }


    public void acceptCustomer(@PathVariable("id") Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User u = optionalUser.get();
            u.setStatus(statusAccepted);
        }
    }

}
