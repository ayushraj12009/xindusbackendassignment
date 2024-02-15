package com.backendAssignmentxindus.Repository;

import com.backendAssignmentxindus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
