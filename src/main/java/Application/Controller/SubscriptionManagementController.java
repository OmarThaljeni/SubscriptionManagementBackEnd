package Application.Controller;

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

    @PostMapping("add-subscription/{id}")
    public Subscription addSubscription(@PathVariable("id") Integer id, @RequestBody Subscription subscriptionRequest) {
        return subscriptionManagementService.addSubscription(id,subscriptionRequest);
    }


    @GetMapping("/all-list-subscriptions")
    public Collection<Subscription> getAllListSubscription() {
        return subscriptionManagementService.getAllListSubscription();
    }



    }
