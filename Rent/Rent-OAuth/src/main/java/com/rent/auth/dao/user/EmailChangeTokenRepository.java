package com.rent.auth.dao.user;

import com.rent.auth.entities.user.EmailChangeToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Duck on 2/3/2017.
 */
public interface EmailChangeTokenRepository extends JpaRepository<EmailChangeToken, Long> {

    EmailChangeToken findByToken(String token);
}
