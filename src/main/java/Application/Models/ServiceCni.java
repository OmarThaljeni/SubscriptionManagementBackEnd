package Application.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "service")
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


}
