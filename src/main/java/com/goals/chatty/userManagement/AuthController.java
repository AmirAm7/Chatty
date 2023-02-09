package com.goals.chatty.userManagement;

import com.goals.chatty.exception.EmailExistsException;
import com.goals.chatty.exception.UsernameExistsException;
import com.goals.chatty.user.mapper.UserMapper;
import com.goals.chatty.userManagement.security.jwt.JwtUtil;
import com.goals.chatty.userManagement.res.request.LoginRequest;
import com.goals.chatty.user.domain.Role;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.userManagement.res.response.JwtResponse;
import com.goals.chatty.userManagement.res.response.UserDto;
import com.goals.chatty.userManagement.registration.RegistrationService;
import com.goals.chatty.userManagement.registration.RegistrationRequest;
import com.goals.chatty.user.service.UserDetailsImpl;
import com.goals.chatty.user.service.UserServiceImpl;
import com.goals.chatty.user.service.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    private final RegistrationService registrationService;
    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    public AuthController(RegistrationService registrationService, UserServiceImpl userService, PasswordEncoder encoder, RoleService roleService, UserMapper userMapper) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @PostMapping(path = "register")
    public String register(@RequestBody @Valid RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }




    @PostMapping(value = "/signing", produces = "application/json", consumes = "application/json")
    public ResponseEntity<JwtResponse> authenticateUser(
            @ApiParam(name = "Login Request", value = "Parameters to login a user") @Valid @RequestBody
            LoginRequest loginRequest) {

        User user = userService.checkPassword(loginRequest.getUsername(), loginRequest.getPassword());
        String jwt = jwtUtil.generateJwtToken(user);

        log.info(jwt);
        UserDetailsImpl userDetails =UserDetailsImpl.build(user);
        List<String> roles =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(
                new JwtResponse(
                        jwt, user.getId(), user.getUsername(), user.getEmail(), roles));
    }


    @ApiOperation(value = "Saves an user in the database")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully created user"),
                    @ApiResponse(
                            code = 403,
                            message = "Accessing the resource you were trying to reach is forbidden"),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
    @PostMapping(value = "/signup")
    public ResponseEntity<User> registerUser (
            @ApiParam(name = "Signup Request", value = "Parameters for registering a user")
            @Valid
            @RequestBody RegistrationRequest registrationRequest
    ) throws UsernameExistsException, EmailExistsException {

        if(userService.existsByUsername(registrationRequest.getUsername().toLowerCase())){
            throw new UsernameExistsException(registrationRequest.getPassword());
        }
        if (userService.existsByEmail(registrationRequest.getEmail())){
            throw new EmailExistsException(registrationRequest.getEmail());
        }

        User newUser = new User();
        newUser.setFirstName(registrationRequest.getFirstName());
        newUser.setLastName(registrationRequest.getLastName());
        newUser.setEmail(registrationRequest.getEmail().toLowerCase());
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(encoder.encode(registrationRequest.getPassword()));

        Set<String> strRoles = registrationRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            final String standard = "user";
            Role userRole = roleService.findByType(standard);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> roles.add(roleService.findByType(role)));
        }

        newUser.setRoles(roles);

        userService.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/findUser/{username}")
    public ResponseEntity<UserDto> getUserByUsername (@PathVariable String username){
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(userMapper.mapEntity(user));
    }

}
