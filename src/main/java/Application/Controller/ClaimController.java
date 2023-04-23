package Application.Controller;

import Application.Models.Claim;
import Application.Services.ClaimsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/SubscriptionManagement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClaimController {
    private final ClaimsService claimsService;

    @GetMapping("/list-claims")
    public Collection<Claim> getListClaims() {
        return claimsService.getListClaims();
    }

    @PostMapping("/add-claim/{id}")
    public Claim addClaims(@PathVariable("id") Integer id, @RequestBody Claim claim) {
        return claimsService.addClaims(id,claim);
    }

    @PutMapping("/update-claim/{id}")
    public Claim updateClaims(@PathVariable("id") Integer id, @RequestBody Claim claimRequest) {
        return claimsService.updateClaims(id,claimRequest);
    }

    @DeleteMapping("/delete-claim/{id}")
    public ResponseEntity<?> deleteClaim(@PathVariable("id") Integer id) {
        return claimsService.deleteClaim(id);
    }



}
