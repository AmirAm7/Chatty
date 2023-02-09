package com.goals.chatty.userManagement.res.response;

import com.goals.chatty.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@ApiModel(value = "JWT token response", description = "Response of a JWT token and it's user")
@Setter
@Getter
public class JwtResponse {
    @ApiModelProperty(
            value = "JWT token",
            example =
                    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW1vdXNlciIsImlhdCI6MTU5ODY2MzIxMSwiZXhwIjoxNTk4NjcwNDExfQ._s8-J7MyL8qsyaJdgL0e3ahNsnaPgKax8mQmOzf8YiORPEBbw52gg9DX_7cUOTdDgbaN7QM4mE1neHFjSrPTow")
    private String token;

    @ApiModelProperty(value = "Type of the JWT token", example = "Bearer")
    private String type = "Bearer";

    @ApiModelProperty(value = "Id of the user", example = "12")
    private Long id;

    @ApiModelProperty(value = "Username of the user", example = "demouser")
    private String username;

    @ApiModelProperty(value = "E-Mail of the user", example = "danko@adesso.de")
    private String email;

    @ApiModelProperty(value = "Roles of the user", example = "[\"ROLE_USER\"]")
    private List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
