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
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Size(max = 30)
    private String typeSubcription;

    private long montantTotal;

    private long montantPaie;


    @ManyToMany(mappedBy = "subscriptions")
    Set<User> userSet;

    @ManyToMany(mappedBy = "subscriptions")
    Set<ServiceCni> serviceCniSet;

    @JsonIgnore
    @ManyToMany(mappedBy = "subscriptions")
    Set<Payment> payments;




}
