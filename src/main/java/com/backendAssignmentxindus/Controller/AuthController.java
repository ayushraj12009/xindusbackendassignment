package com.backendAssignmentxindus.Controller;

import com.backendAssignmentxindus.Config.JwtProvider;
import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Repository.UserRepository;
import com.backendAssignmentxindus.Service.CustomerUserDetailsService;
import com.backendAssignmentxindus.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) {
        try {
            User isExist = userRepository.findByEmail(user.getEmail());
            if (isExist != null) {
                throw new Exception("Email already used with another account");
            }

            User newUser = new User();
            newUser.setUserName(user.getUserName());
            newUser.setFristName(user.getFristName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));

            User savedUser = userRepository.save(newUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            String token = JwtProvider.generateToken(authentication);

            AuthResponse response = new AuthResponse(token, "Signup Success");
            return response;
        } catch (Exception e) {

            return new AuthResponse(null, e.getMessage());
        }
    }


    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LogginRequest logginRequest){
        try {
            Authentication authentication = authenticate(logginRequest.getEmail(), logginRequest.getPassword());

            String token = JwtProvider.generateToken(authentication);

            AuthResponse response = new AuthResponse(token, "Login Success");
            return response;
        }
        catch (Exception e){
            return new AuthResponse(null, e.getMessage());
        }
    }




//    @PostMapping("/signup")
//    public AuthResponse createUser(@RequestBody User user) throws Exception {
//
//        try {
//            User isExist = userRepository.findByEmail(user.getEmail());
//            if (isExist != null) {
//                throw new Exception("Email already used with another account");
//            }
//
//            User newUser = new User();
//            newUser.setUserName(user.getUserName());
//            newUser.setFristName(user.getFristName());
//            newUser.setLastName(user.getLastName());
//            newUser.setEmail(user.getEmail());
//            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
//
//            User savedUser = userRepository.save(newUser);
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
//            String token = JwtProvider.generateToken(authentication);
//
//            AuthResponse response = new AuthResponse(token, "Signup Success");
//            return response;
//        } catch (Exception e) {
//
//            throw new Exception("Invalid email format or other error occurred: " + e.getMessage());
//        }
//    }



//    @PostMapping("/signin")
//    public AuthResponse signin(@RequestBody LogginRequest logginRequest){
//        Authentication authentication = authenticate(logginRequest.getEmail(), logginRequest.getPassword());
//
//        String token = JwtProvider.generateToken(authentication);
//
//        AuthResponse response = new AuthResponse(token, "Login Sucess");
//        return response;
//
//    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if(userDetails == null){
            throw new BadCredentialsException("Invalid UserName");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

    }


}
