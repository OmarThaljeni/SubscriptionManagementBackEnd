package Application.Services;

import Application.Models.Claim;
import Application.Models.Payment;
import Application.Models.User;
import Application.Repository.PaymentRepository;
import Application.Repository.SubscriptionRepository;
import Application.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Collection<Payment> getListPayments() {
        return paymentRepository.findAll();
    }

/*
    public Payment addPaiement(@PathVariable("id") Integer id, @RequestBody Payment payment) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User u = optionalUser.get();
            var paiementGenerted = Payment.builder()
                    .montantTotal(payment.getMontantTotal())
                    .montantPaie(payment.getMontantPaie())
                    .mo
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
*/


}
