package com.rent.api.dao.user;

import com.rent.api.entities.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Duck on 1/27/2017.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);

    UserInfo findById(int id);
}
