package com.example.movieworld.user.repository;

import com.example.movieworld.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUserEmail(String userEmail);
    User findByUserEmail(String userEmail);
}
