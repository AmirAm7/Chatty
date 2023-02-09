package com.goals.chatty.userManagement.service;

import com.goals.chatty.user.domain.UserRepository;
import com.goals.chatty.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(value = MockitoExtension.class)
class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository mockUserRepository;

    @Test
    public void giveUser_whenUserFoundByUsername() {
        when(mockUserRepository.findUserByUsername(any())).thenReturn(null);
        userService.findByUsername("username");
    }
}



