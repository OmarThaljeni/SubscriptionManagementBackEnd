package Application.Services;


import Application.Models.ServiceCni;
import Application.Repository.ServiceCniRepository;
import Application.Repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCniService {
    private final ServiceCniRepository serviceRepository;

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
