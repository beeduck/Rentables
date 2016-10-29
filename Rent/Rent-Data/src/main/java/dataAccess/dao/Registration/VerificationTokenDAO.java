package dataAccess.dao.Registration;

import dataAccess.entities.Registration.VerificationToken;

/**
 * Created by Duck on 10/28/2016.
 */
public interface VerificationTokenDAO {

    boolean saveVerificationToken(VerificationToken token);

    VerificationToken getTokenByString(String token);

    boolean removeToken(VerificationToken verificationToken);
}
