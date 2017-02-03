package com.rent.auth.proxy;

import com.rent.utility.Constants;
import com.rent.utility.dto.NewUserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by duck on 11/19/16.
 */
@FeignClient(name = "Rent-API")
public interface RentAPIProxy {

    @RequestMapping(method = RequestMethod.POST,
                    value = Constants.SHARED_BASE_PATH + Constants.SHARED_COMPLETE_USER_REGISTRATION_PATH,
                    consumes = "application/json")
    void registerUser(NewUserDTO newUserDTO);

    @RequestMapping(method = RequestMethod.PUT,
                    value = Constants.SHARED_BASE_PATH + Constants.SHARED_CHANGE_USER_EMAIL_PATH,
                    consumes = "application/json")
    void updateUserEmail(NewUserDTO newUserDTO);
}
