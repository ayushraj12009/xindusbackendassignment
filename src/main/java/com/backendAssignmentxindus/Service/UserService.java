package com.backendAssignmentxindus.Service;

import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
       return userRepository.save(user);
    }
}
