package com.goals.chatty.user.service;

import com.goals.chatty.user.config.ERole;
import com.goals.chatty.user.domain.Role;
import com.goals.chatty.user.domain.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(int id) {
        return roleRepository
                .findById((long) id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
    }

    @Override
    public Role findByType(String type) {
        Role role = new Role();

        switch (type.toLowerCase()) {
            case "user":
                role =
                        roleRepository
                                .findByType(ERole.ROLE_USER)
                                .orElseThrow(
                                        () ->
                                                new EntityNotFoundException(
                                                        "Role not found with type: " + ERole.ROLE_USER.getName()));
                break;
            case "moderator":
                role =
                        roleRepository
                                .findByType(ERole.ROLE_MODERATOR)
                                .orElseThrow(
                                        () ->
                                                new EntityNotFoundException(
                                                        "Role not found with type: " + ERole.ROLE_MODERATOR.getName()));
            default:
                throw new EntityNotFoundException("Role not found with type: " + role);
        }

        return role;
    }
}


