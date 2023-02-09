package com.goals.chatty.userManagement.res.response;


import com.goals.chatty.user.config.ERole;
import com.goals.chatty.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstname;
    private String lastname;
    private String username;
    private Set<String> role;
}
