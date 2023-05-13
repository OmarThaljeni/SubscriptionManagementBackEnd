package Application.Services;

import Application.Models.Claim;
import Application.Models.Payment;
import Application.Models.Subscription;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    public Collection<Payment> getListPayments() {
        return paymentRepository.findAll();
    }


    public Payment addPaiement(@PathVariable("id") Integer id, @RequestBody Payment payment) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        if (optionalSubscription.isPresent()) {
            Subscription s = optionalSubscription.get();
            s.setMontantPaie(s.getMontantPaie() + payment.getMontantPaie());
            Set<Subscription> subscriptionSet = new HashSet<>();
            subscriptionSet.add(s);
            var paiementGenerted = Payment.builder()
                    .montantPaie(payment.getMontantPaie())
                    .montantReste((int)s.getMontantTotal() - payment.getMontantPaie())
                    .datePayment(payment.getDatePayment())
                    .observation(payment.getObservation())
                    .subscriptions(subscriptionSet)
                    .build();
            paymentRepository.save(paiementGenerted);
            subscriptionRepository.save(s);
            return paiementGenerted;
        } else return null;
    }



}
