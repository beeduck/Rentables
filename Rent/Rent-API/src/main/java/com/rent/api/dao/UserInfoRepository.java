package com.rent.api.dao;

import com.rent.api.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Duck on 1/27/2017.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    List<UserInfo> findByUsername(String username);
}
