package com.goals.chatty.user.domain;

import com.goals.chatty.userManagement.registration.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User save (RegistrationRequest registrationRequest);
    Optional<User> findUserByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findUserByUsernameIgnoreCase(String username);
    Optional<Object> findUserByEmail(String email);
    Optional<Object> findById(long user_id);

}