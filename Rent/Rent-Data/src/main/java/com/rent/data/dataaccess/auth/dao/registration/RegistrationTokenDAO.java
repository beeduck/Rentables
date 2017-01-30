package com.rent.data.dataaccess.auth.dao.registration;

import com.rent.auth.entities.registration.RegistrationToken;

/**
 * Created by Duck on 10/28/2016.
 */
public interface RegistrationTokenDAO {

    boolean saveRegistrationToken(RegistrationToken token);

    RegistrationToken getTokenByString(String token);

    boolean removeToken(RegistrationToken registrationToken);
}
