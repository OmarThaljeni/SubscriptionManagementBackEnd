package Application.Security.Payload;


import Application.Models.Role;
import lombok.*;

import java.util.Set;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String adress;
    private String password;
    private Set<Role> roles;

}
