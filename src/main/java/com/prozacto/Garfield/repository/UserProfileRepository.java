package com.prozacto.Garfield.repository;

import com.prozacto.Garfield.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>,
        JpaSpecificationExecutor<UserProfile> {

    @Query(value = "SELECT UP from UserProfile UP where UP.userName = :userName")
    UserProfile getUserProfile(@Param("userName") String userName);

    @Query(value = "UPDATE UserProfile UP set UP.salt = :salt where UP.userName = :userName")
    void setSalt(@Param("salt") String salt, @Param("userName") String userName);

    @Query(value = "UPDATE UserProfile UP set UP.userPassword = :userPassword where UP.userName = :userName")
    void setUserPassword(@Param("userPassword") String userPassword, @Param("userName") String userName);

    @Query(value = "UPDATE UserProfile UP set UP.token = :token where UP.userName = :userName")
    void setToken(@Param("token") String token, @Param("userName") String userName);
}

