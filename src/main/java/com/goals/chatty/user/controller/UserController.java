package com.goals.chatty.user.controller;


import com.goals.chatty.user.domain.User;
import com.goals.chatty.user.mapper.UserMapper;
import com.goals.chatty.userManagement.res.response.UserDto;
import com.goals.chatty.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @Autowired
    private  UserService userService;
    @Autowired
    private  UserMapper userMapper;

    @GetMapping("/findUser/{username}")
    public ResponseEntity<UserDto> getUserByUsername (@PathVariable String username){
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(userMapper.mapEntity(user), HttpStatus.OK);
    }

    @PostMapping("/flowUser/{username}")
    public ResponseEntity<Void> flowOtherUser (@PathVariable String username){
        userService.flowUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allFollowers/{user_id}")
    public ResponseEntity <List<String>> findAllFollowersByUserId(@PathVariable long user_id){
        List<String> followers = userService.giveAllFollowers(user_id);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/allFolloweds/{user_id}")
    public ResponseEntity <List<String>> findAllFollowedsByUserId(@PathVariable long user_id){
        List<String> followers = userService.giveAllFolloweds(user_id);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }



}
