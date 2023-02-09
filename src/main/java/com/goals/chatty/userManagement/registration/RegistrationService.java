package com.goals.chatty.userManagement.registration;

import com.goals.chatty.user.domain.UserRepository;
import com.goals.chatty.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements  Registration{

    private final UserRepository userRepository;
    private final UserServiceImpl userService;


    @Autowired
    public RegistrationService(UserRepository userRepository, UserServiceImpl userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public String register(RegistrationRequest registrationRequest) {
        return null;
    }

/*
    public String register(RegistrationRequest request) {
        if(userRepository.findUserByUsername(request.getUsername()) != null){
            return "Username: '" + request.getUsername() + "' already exists";
        }
        return userService.signUpUser(
                new User(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getUsername(),
                    request.getPassword()
                )
        );
    }

 */
}
