package com.prozacto.Garfield.repository;

import com.prozacto.Garfield.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>,
        JpaSpecificationExecutor<UserProfile> {

    @Query(value = "SELECT UP from UserProfile UP where UP.userName = :userName")
    UserProfile getUserProfile(@Param("userName") String userName);

    @Query(value = "SELECT UP from UserProfile UP where UP.id = :id")
    UserProfile getUserProfileById(@Param("id") Integer id);

    @Query(value = "SELECT UP from UserProfile UP where UP.userEmail = :userEmail")
    UserProfile getUserProfileByUserEmail(@Param("userEmail") String userEmail);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO user_profile "
                                       + "(first_name, last_name, user_password, user_name, role, created_on, user_email)"
                                       + " VALUES ( :firstName, :lastName, :userPassword, :userName, :role, :createdOn, :userEmail)")
    void insert(@Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("userPassword") String userPassword,
                @Param("userName") String userName,
                @Param("role") String role,
                @Param("createdOn") String createdOn,
                @Param("userEmail") String userEmail
               );
}

