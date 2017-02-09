package com.rent.auth.dao.user;

import com.rent.auth.entities.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Duck on 1/29/2017.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByUsername(String username);

    UserDetails findById(int id);

    @Query("SELECT (COUNT(*)>0) FROM UserDetails WHERE LOWER(username) = LOWER(:username)")
    boolean exists(@Param("username") String username);
}
