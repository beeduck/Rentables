package com.rent.auth.dao.registration;

import com.rent.auth.entities.registration.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Duck on 1/29/2017.
 */
public interface RegistrationRepository extends JpaRepository<RegistrationToken, Long> {

    RegistrationToken findByToken(String token);
}
