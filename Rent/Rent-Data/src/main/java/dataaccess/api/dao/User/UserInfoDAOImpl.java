package dataaccess.api.dao.User;

import dataaccess.api.dao.ApiDAO;
import dataaccess.api.entities.user.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Duck on 11/11/2016.
 */
@Repository("userInfoDao")
public class UserInfoDAOImpl extends ApiDAO implements UserInfoDAO {

    @Transactional
    public boolean addUser(int userId, String username) {
        UserInfo userDetails = new UserInfo(userId, username);

        return this.save(userDetails);
    }
}
