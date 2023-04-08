package Application.Security.Payload;

import lombok.*;

import java.util.Set;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String phone;
  private String adress;
  private String password;
  private Set<String> roles;

}
