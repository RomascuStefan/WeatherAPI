package com.example.demo.Repository;

import com.example.demo.Model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileDAO  extends JpaRepository<UserProfile, Long> {

    @Query("SELECT p FROM UserProfile p WHERE p.email = :email")
    Optional<UserProfile> findByEmail(String email);

}
