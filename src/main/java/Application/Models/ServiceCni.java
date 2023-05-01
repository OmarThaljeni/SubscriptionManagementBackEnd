package Application.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "serviceCni")
public class ServiceCni {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Size(max = 30)
    private String typeService;

    @NotBlank
    @Size(max = 30)
    private String modelService;

    @NotBlank
    @Size(max = 30)
    private String namePc;


    @NotBlank
    @Size(max = 30)
    private String ramPc;

    @NotBlank
    @Size(max = 30)
    private String cpuPc;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "serviceCni_subscriptions",
            joinColumns = @JoinColumn(name = "service_cni_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    Set<Subscription> subscriptions;



}
