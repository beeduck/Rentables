package com.rent.api.controllers;

import com.rent.api.dao.user.UserInfoRepository;
import com.rent.api.entities.user.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class GeneralController {

    @Resource(name = "userInfoRepository")
    UserInfoRepository userInfoRepository;

//    @PreAuthorize(Constants.OAUTH2_AUTH_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/")
    public List<Integer> index() {
        return null;
    }

    @RequestMapping("/test")
    public void test() {
        UserInfo userInfo = userInfoRepository.findByUsername("b33duck@gmail.com");
        UserInfo userInfo2 = userInfoRepository.findById(483);

        UserInfo userInfo3 = new UserInfo(4999, "Kris");
        userInfoRepository.save(userInfo3);

        userInfo3 = userInfoRepository.findById(4999);
        userInfo3.setUsername("tony");

        userInfoRepository.save(userInfo3);

        System.out.println("Wait");
    }
}