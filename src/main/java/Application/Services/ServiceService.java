package Application.Services;


import Application.Models.Claim;
import Application.Models.ServiceCni;
import Application.Models.Subscription;
import Application.Models.User;
import Application.Repository.ServiceRepository;
import Application.Repository.SubscriptionRepository;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Collection<ServiceCni> getListeService() {
        return serviceRepository.findAll();
    }

    public ServiceCni addServiceCni(@RequestBody ServiceCni serviceCni) {
            var serviceCniGenerated = ServiceCni.builder()
                    .typeService(serviceCni.getTypeService())
                    .modelService(serviceCni.getModelService())
                    .namePc(serviceCni.getNamePc())
                    .ramPc(serviceCni.getRamPc())
                    .cpuPc(serviceCni.getCpuPc())
                    .build();
            serviceRepository.save(serviceCniGenerated);
            return serviceCniGenerated;
    }





}
