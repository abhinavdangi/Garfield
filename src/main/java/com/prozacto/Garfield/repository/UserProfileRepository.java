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
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>,
        JpaSpecificationExecutor<UserProfile> {

    @Query(value = "SELECT UP from UserProfile UP where UP.userName = :userName")
    UserProfile getUserProfile(@Param("userName") String userName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE UserProfile UP set UP.salt = :salt where UP.userName = :userName")
    void setSalt(@Param("salt") String salt, @Param("userName") String userName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE UserProfile UP set UP.userPassword = :userPassword where UP.userName = :userName")
    void setUserPassword(@Param("userPassword") String userPassword,
                         @Param("userName") String userName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE UserProfile UP set UP.token = :token, UP.createdOn = :createdOn where UP.userName = :userName")
    void setToken(@Param("token") String token,
                  @Param("userName") String userName,
                  @Param("createdOn") String createdOn);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO user_profile "
                                       + "(first_name, last_name, salt, user_password, user_name, role, created_on)"
                                       + " VALUES ( :firstName, :lastName, :salt, :userPassword, :userName, :role, :createdOn)")
    void insert(@Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("salt") String salt,
                @Param("userPassword") String userPassword,
                @Param("userName") String userName,
                @Param("role") String role,
                @Param("createdOn") String createdOn
               );
}

