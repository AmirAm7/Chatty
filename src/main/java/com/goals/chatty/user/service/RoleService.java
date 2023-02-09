package com.goals.chatty.user.service;
import com.goals.chatty.user.domain.Role;

public interface RoleService {

    Role findById(int id);
    Role findByType(String type);
}
