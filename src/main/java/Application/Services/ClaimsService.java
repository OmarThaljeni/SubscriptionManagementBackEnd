package Application.Services;

import Application.Models.*;
import Application.Repository.ClaimRepository;
import Application.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClaimsService {
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;

    public Collection<Claim> getListClaims() {
        return claimRepository.findAll();
    }

    public Claim addClaims(@PathVariable("id") Integer id, @RequestBody Claim claim) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User u = optionalUser.get();
            var claimsGenerated = Claim.builder()
                    .subject(claim.getSubject())
                    .body(claim.getBody())
                    .priority(claim.getPriority())
                    .status("En Attente")
                    .user(u)
                    .build();
            claimRepository.save(claimsGenerated);
            return claimsGenerated;
        } else return null;
    }

    public Claim updateClaims(@PathVariable("id") Integer id, @RequestBody Claim claimRequest) {
        Optional<Claim> claimOptional = claimRepository.findById(id);
        if (claimOptional.isPresent()) {
            Claim c = claimOptional.get();
            c.setStatus(claimRequest.getStatus());
            var claimsGenerated = Claim.builder()
                    .subject(c.getSubject())
                    .body(c.getBody())
                    .status(c.getStatus())
                    .priority(c.getPriority())
                    .build();
            claimRepository.save(claimsGenerated);
            return claimsGenerated;
        } else return null;
    }

    public ResponseEntity<?> deleteClaim(@PathVariable("id") Integer id) {
        try {
            claimRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
