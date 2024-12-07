package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositery extends JpaRepository<User,Integer> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username")
    boolean existsByUsername(String username);

    User findByUsername(String username);

    List<User> findByUserrole(String userrole);

}
