package com.goals.chatty.user.service;

import com.goals.chatty.user.domain.User;
import com.goals.chatty.userManagement.registration.RegistrationRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import java.util.Optional;


public interface UserService {

    Optional<User> getCurrentUser();
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean existsByUsername(String username);
    void save(User user);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    User findByUsername(String username);
    User findByUsernameIgnoreCase(String username);
    List<String> giveAllFollowers(long user_id);
    void flowUser(String username);
    User signUpUser(RegistrationRequest newUser);
    List<String> giveAllFolloweds (long user_id);
    User findUserByUsername(String username);
}
