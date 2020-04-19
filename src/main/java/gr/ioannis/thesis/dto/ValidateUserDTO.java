package gr.ioannis.thesis.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ValidateUserDTO {

  @Length(min = 1, max = 255)
  private String username;

  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W\\_])[A-Za-z\\d\\W\\_]{8,}$")
  private String password;

  @Email
  private String email;

  @Length(min = 1, max = 255)
  private String fullName;

}
