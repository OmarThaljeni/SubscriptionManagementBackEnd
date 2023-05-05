package Application.Controller;

import Application.Models.ServiceCni;
import Application.Models.Subscription;
import Application.Services.SubscriptionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/SubscriptionManagement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubscriptionManagementController {

    private final SubscriptionManagementService subscriptionManagementService;

    @GetMapping("/list-subscriptions/{id}")
    public Collection<Subscription> getListSubscription(@PathVariable("id") Integer id) {
        return subscriptionManagementService.getListSubscription(id);
    }

    @PostMapping("/add-subscription/{idCustomer}/{idService}/{idSubscription}")
    public ServiceCni addSubscriptionToService(@PathVariable("idCustomer") Integer idCustomer, @PathVariable("idService") Integer idService, @PathVariable("idSubscription") Integer idSubscription) {
        return subscriptionManagementService.addSubscriptionToService(idCustomer,idService,idSubscription);
    }

    @PostMapping("/add-subscription-user/{idCustomer}/{idService}")
    public Subscription addSubscriptionToUser(@PathVariable("idCustomer") Integer idCustomer, @PathVariable("idService") Integer idService,@RequestBody Subscription subscriptionRequest) {
        return subscriptionManagementService.addSubscriptionToUser(idCustomer,idService,subscriptionRequest);
    }


    @GetMapping("/all-list-subscriptions")
    public Collection<Subscription> getAllListSubscription() {
        return subscriptionManagementService.getAllListSubscription();
    }



    }
