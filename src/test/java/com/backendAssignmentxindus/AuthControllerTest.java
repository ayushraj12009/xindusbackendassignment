package com.backendAssignmentxindus;

import com.backendAssignmentxindus.Controller.AuthController;
import com.backendAssignmentxindus.Controller.AuthResponse;
import com.backendAssignmentxindus.Controller.LogginRequest;
import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Repository.UserRepository;
import com.backendAssignmentxindus.Service.CustomerUserDetailsService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerUserDetailsService customerUserDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        User newUser = new User();
        newUser.setUserName("AyushRaj12009");
        newUser.setFristName("Ayush");
        newUser.setLastName("Raj");
        newUser.setEmail("ayushraj12009@gmail.com");
        newUser.setPassword("AyushRaj@#2024");

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");

        AuthResponse response = authController.createUser(newUser);
        assertEquals("Signup Success", response.getMessage());

    }

    @Test
    public void testCreateUser_EmailExists() {
        User existingUser = new User();
        existingUser.setUserName("AyushRaj12009");
        existingUser.setFristName("Ayush");
        existingUser.setLastName("Raj");
        existingUser.setEmail("ayushraj12009@gmail.com");
        existingUser.setPassword("AyushRaj@#2024");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);

        AuthResponse response = authController.createUser(existingUser);
        assertEquals("Email already used with another account", response.getMessage());
    }



//    @Test
//    public void testSignIn_Success() {
//        String email = "ayushraj12009@gmail.com";
//        String password = "AyushRaj@#2024";
//
//        // Create a simple UserDetails object with a valid username, password, and granted authority
//      //  UserDetails userDetails = new User("ayushraj12009@gmail.com", "AyushRaj@#2024", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
//
//        when(customerUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
//        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);
//
//        LogginRequest logginRequest = new LogginRequest(email, password);
//        AuthResponse response = authController.signin(logginRequest);
//        assertEquals("Login Success", response.getMessage());
//    }



    @Test
    public void testSignIn_InvalidCredentials() {
        String email = "ayushraj12009@example.com";
        String password = "RajAyushRaj2023";
        when(customerUserDetailsService.loadUserByUsername(email)).thenReturn(null);

        LogginRequest logginRequest = new LogginRequest(email, password);
        AuthResponse response = authController.signin(logginRequest);
        assertEquals("Invalid UserName", response.getMessage());
    }
}