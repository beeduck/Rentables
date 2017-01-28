package com.rent.api.controllers;

import com.rent.api.dao.UserInfoRepository;
import com.rent.api.entities.UserInfo;
import com.rent.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        List<UserInfo> userInfos = userInfoRepository.findByUsername("b33duck@gmail.com");
        System.out.println("Wait");
    }
}