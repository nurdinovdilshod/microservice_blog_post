package com.company.repositories;

import com.company.entity.UserSMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSMSRepository extends JpaRepository<UserSMS, Integer> {
    @Query("select u from UserSMS u where u.userId = ?1")
    UserSMS findByUserId(Integer userId);
}