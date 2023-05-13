package Application.Controller;


import Application.Models.Payment;
import Application.Services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/SubscriptionManagement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/liste-paiements")
    public Collection<Payment> getListPayments() {
        return paymentService.getListPayments();
    }


    @PostMapping("/add-paiement/{id}")
    public Payment addPaiement(@PathVariable("id") Integer id, @RequestBody Payment payment) {
        return paymentService.addPaiement(id, payment);
    }

}