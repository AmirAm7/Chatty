package com.goals.chatty.user.mapper;
import com.goals.chatty.user.config.ERole;
import com.goals.chatty.user.domain.Role;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.userManagement.res.response.UserDto;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper implements Mapper<UserDto, User> {
    @Override
    public UserDto mapEntity(User entity) {
        UserDto user = new UserDto();
        user.setFirstname(entity.getFirstName());
        user.setLastname(entity.getLastName());
        user.setUsername(entity.getUsername());
        user.setRole(entity.getRoles().stream().map(Role::getType).map(ERole::getName).collect(Collectors.toSet()));
        return user;
    }

    @Override
    public User mapDomainObject(UserDto domainObject) {
        return null;
    }
}
