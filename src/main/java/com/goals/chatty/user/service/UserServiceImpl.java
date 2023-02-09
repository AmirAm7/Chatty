package com.goals.chatty.user.service;

import com.goals.chatty.user.domain.User;
import com.goals.chatty.user.domain.UserRepository;
import com.goals.chatty.userManagement.config.CurrentUser;
import com.goals.chatty.userManagement.registration.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final static String USER_NOT_FOUND_MSG_username = "user with username %s not found";
    private final static String USER_NOT_FOUND_MSG_userId = "user with id %s not found";
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    private Optional<User> user;


    @Autowired
    public UserServiceImpl(CurrentUser currentUser, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> getCurrentUser() {
        User cUser = userRepository.findUserByUsername(currentUser.getUsername()).orElseThrow(() -> new IllegalStateException("CurrentUser not found"));
        return Optional.ofNullable(cUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG_username, username)));
        return UserDetailsImpl.build(user);
    }
    @Override
    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG_username, username)));
    }
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findUserByUsernameIgnoreCase(username).isPresent();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG_username, email)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG_username, username)));
    }

    @Override
    public User findByUsernameIgnoreCase(String username) {
        return userRepository.findUserByUsernameIgnoreCase(username).orElseThrow(()-> new UsernameNotFoundException(
               String.format(USER_NOT_FOUND_MSG_username, username)));
    }

    @Override
    public List<String> giveAllFollowers  (long user_id) {
        Optional<User> user = findById(user_id);
        if (user.isPresent()){
            List <User> followers = user.get().getFolloweds();
            return followers.stream().map(User::getUsername).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_MSG_userId, user_id));
        }

    }

    @Override
    public List<String> giveAllFolloweds (long user_id){
        Optional<User> user = findById(user_id);
        if(user.isPresent()){
            List<User> followeds = user.get().getFolloweds();
            return followeds.stream().map(User::getUsername).collect(Collectors.toList());
        }
        else{
            throw new EntityNotFoundException(String.format(USER_NOT_FOUND_MSG_userId, user_id));
        }
    }

    @Override
    public void flowUser(String username) {
        User user = findByUsername(username);
        User currentUser = getCurrentUser().get();
        boolean isFollowed = currentUser.getFolloweds().stream().anyMatch(follower -> follower.getId() == user.getId());
        if (!isFollowed) {
            List<User> followers = user.getFollowers();
            List<User> followeds = currentUser.getFolloweds();
            followers.add(currentUser);
            followeds.add(user);
            user.setFollowers(followers);
            currentUser.setFolloweds(followeds);
            userRepository.save(user);
            userRepository.save(currentUser);
            log.info("User {} had added user {} ", currentUser.getFirstName(), user.getFirstName());
        }
    }

    public User checkPassword (String username, String pass){
        User user = userRepository.findUserByUsername(username).orElseThrow();
        if (bCryptPasswordEncoder.matches(pass, user.getPassword())) {
            return  user;
        }
        return null;
    }

    @Override
    public User signUpUser(RegistrationRequest newUser){
        boolean userFindByEmail = userRepository.findByEmail(newUser.getEmail()).isPresent();
        boolean userFindByUsername = userRepository.findByEmail(newUser.getEmail()).isPresent();
        if (userFindByEmail || userFindByUsername){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);
        return userRepository.save(newUser);
    }
}

