package Application.Security.Payload;

import lombok.*;

import java.util.Set;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Set<String> roles;

}
