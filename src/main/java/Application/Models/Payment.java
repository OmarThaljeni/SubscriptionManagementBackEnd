package Application.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private int montantPaie;

    @NotBlank
    private int montantReste;

    @NotBlank
    private LocalDate datePayment;

    private String observation;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "payments_subscriptions",
            joinColumns = @JoinColumn(name = "payment_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    Set<Subscription> subscriptions;


}


