package Application.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Size(max = 30)
    private String subject;

    @NotBlank
    @Size(max = 80)
    private String body;

    @NotBlank
    @Column(length = 100000)
    private String priority;


    @NotBlank
    @Size(max = 30)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;


}
