package com.goals.chatty.userManagement.registration;


import com.goals.chatty.userManagement.security.valitation.ValidEmail;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    @NotBlank(message = "firstName is required")
    @Size(min = 3, max = 255)
    @NotNull
    private String firstName;
    @NotBlank(message = "lastName is required")
    @Size(min = 3, max = 255)
    @NotNull
    private String lastName;
    @ApiModelProperty(value = "E-Mail of user", example = "heyhey@adesso.de", required = true)
    @NotBlank(message = "E-Mail is required")
    @NotNull
    @Size(max = 70)
    @ValidEmail
    @Email
    private String email;
    @ApiModelProperty(value = "Username of user", example = "demouser", required = true)
    @NotBlank(message = "Username is required")
    @NotNull
    @Size(min = 3, max = 255)
    private String username;
    @NotNull
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50)
    private String password;
    @NotNull
    private Set<String> roles;
}
