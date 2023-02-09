package com.goals.chatty.userManagement.res.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "Login request", description = "Request to login an user")
@Data
public class LoginRequest {
        @ApiModelProperty(value = "Username of user", example = "demouser", required = true)
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 255)
        private String username;

        @ApiModelProperty(value = "Password of user", example = "Ab1!aa", required = true)
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 50)
        private String password;
    }
