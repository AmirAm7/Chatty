package com.goals.chatty.userManagement.config;

import com.goals.chatty.user.domain.Role;
import com.goals.chatty.user.domain.User;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CurrentUser {

    private String username;
    private long userId;
    private Role role;



}
