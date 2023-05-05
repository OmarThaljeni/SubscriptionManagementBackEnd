package Application.Services;

import Application.Models.ServiceCni;
import Application.Models.Subscription;
import Application.Models.User;
import Application.Repository.ServiceCniRepository;
import Application.Repository.SubscriptionRepository;
import Application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriptionManagementService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ServiceCniRepository serviceCniRepository;

    public Collection<Subscription> getListSubscription(Integer id) {
        List<Subscription> subscriptionList = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            subscriptionList = subscriptionRepository.findAllByUserId(id);
        }
        return subscriptionList;
    }

    public ServiceCni addSubscriptionToService(@PathVariable("idCustomer") Integer idCustomer, @PathVariable("idService") Integer idService, @PathVariable("idSubscription") Integer idSubscription) {
        Optional<User> optionalUser = userRepository.findById(idCustomer);
        Optional<ServiceCni> optionalServiceCni =  serviceCniRepository.findById(idService);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(idSubscription);
        if (optionalUser.isPresent() && optionalServiceCni.isPresent() && optionalSubscription.isPresent()) {
            User user = optionalUser.get();
            ServiceCni serviceCni = optionalServiceCni.get();
            Subscription subscription = optionalSubscription.get();

                    serviceCni.getSubscriptions().add(subscription);
                    user.getSubscriptions().add(subscription);
                    userRepository.save(user);
                    serviceCniRepository.save(serviceCni);

            return ServiceCni.builder()
                    .id(serviceCni.getId())
                    .build();
        } else return null;
    }

    public Collection<Subscription> getAllListSubscription() {
        return subscriptionRepository.findAll();
    }

    public Subscription addSubscriptionToUser(@PathVariable("idCustomer") Integer idCustomer, @PathVariable("idService") Integer idService, @RequestBody Subscription subscriptionRequest) {
        Optional<User> optionalUser = userRepository.findById(idCustomer);
        Optional<ServiceCni> optionalServiceCni =  serviceCniRepository.findById(idService);
        Set<User> setUser = new HashSet<>();
        Set<ServiceCni> serviceCniSet = new HashSet<>();
        Set<Subscription> subscriptionSet = new HashSet<>();
        if (optionalUser.isPresent() && optionalServiceCni.isPresent()) {
            User user = optionalUser.get();
            ServiceCni serviceCni = optionalServiceCni.get();
            setUser.add(user);
            serviceCniSet.add(serviceCni);
            var subscriptionGenerated = Subscription.builder()
                    .typeSubcription(subscriptionRequest.getTypeSubcription())
                    .userSet(setUser)
                    .serviceCniSet(serviceCniSet)
                    .build();
            subscriptionRepository.save(subscriptionGenerated);
            if(user.getSubscriptions().size()>0 && serviceCni.getSubscriptions().size()>0) {
                serviceCni.getSubscriptions().add(subscriptionGenerated);
                user.getSubscriptions().add(subscriptionGenerated);
                userRepository.save(user);
                serviceCniRepository.save(serviceCni);
            } else {
                subscriptionSet.add(subscriptionGenerated);
                user.setSubscriptions(subscriptionSet);
                serviceCni.setSubscriptions(subscriptionSet);
                userRepository.save(user);
                serviceCniRepository.save(serviceCni);
            }
            return Subscription.builder().
                    id(subscriptionGenerated.getId())
                    .typeSubcription(subscriptionGenerated.getTypeSubcription())
                    .userSet(setUser)
                    .serviceCniSet(serviceCniSet)
                    .build();
        } else return null;
    }


}