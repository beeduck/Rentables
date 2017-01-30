package com.rent.auth.dao.user;

import com.rent.auth.entities.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Duck on 1/29/2017.
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByUsername(String username);

    UserDetails findById(int id);
}
