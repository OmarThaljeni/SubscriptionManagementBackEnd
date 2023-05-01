package Application.Controller;


import Application.Models.ServiceCni;
import Application.Services.ServiceCniService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/SubscriptionManagement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceCniController {

    private final ServiceCniService serviceCniService;

    @GetMapping("/list-services-cni")
    public Collection<ServiceCni> getListeService() {
        return serviceCniService.getListeService();
    }

    @PostMapping("/add-service-cni")
    public ServiceCni addServiceCni(@RequestBody ServiceCni serviceCni) {
        return serviceCniService.addServiceCni(serviceCni);
    }



    }
