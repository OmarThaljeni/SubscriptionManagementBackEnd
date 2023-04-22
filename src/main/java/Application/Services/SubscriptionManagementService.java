package Application.Services;

import Application.Models.ERole;
import Application.Models.Role;
import Application.Models.Subscription;
import Application.Models.User;
import Application.Repository.SubscriptionRepository;
import Application.Repository.UserRepository;
import Application.Security.Payload.AuthenticationResponse;
import Application.Security.Payload.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriptionManagementService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Collection<Subscription> getListSubscription(Integer id) {
        List<Subscription> subscriptionList = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            subscriptionList = subscriptionRepository.findAllByUserId(id);
        }
        return subscriptionList;
    }

    public Subscription addSubscription(@PathVariable("id") Integer id, @RequestBody Subscription subscriptionRequest) {
        Optional<User> optionalUser = userRepository.findById(id);
        Set<User> setUser = new HashSet<>();
        Set<Subscription> subscriptionSet = new HashSet<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            setUser.add(user);
            var subscriptionGenerated = Subscription.builder()
                    .typeSubcription(subscriptionRequest.getTypeSubcription())
                    .userSet(setUser)
                    .build();
                subscriptionRepository.save(subscriptionGenerated);
                if(user.getSubscriptions().size()>0) {
                    user.getSubscriptions().add(subscriptionGenerated);
                    userRepository.save(user);
                } else {
                    subscriptionSet.add(subscriptionGenerated);
                    user.setSubscriptions(subscriptionSet);
                    userRepository.save(user);
                }
            return Subscription.builder().
                    id(subscriptionGenerated.getId())
                    .typeSubcription(subscriptionGenerated.getTypeSubcription())
                    .userSet(setUser)
                    .build();
        } else return null;
    }

    public Collection<Subscription> getAllListSubscription() {
        return subscriptionRepository.findAll();
    }

    }