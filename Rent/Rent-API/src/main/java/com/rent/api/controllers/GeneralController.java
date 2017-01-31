package com.rent.api.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GeneralController {

//    @PreAuthorize(Constants.OAUTH2_AUTH_USER)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/")
    public List<Integer> index() {
        return null;
    }
}