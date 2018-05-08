package com.jfms.aaa.api;



import com.jfms.aaa.UserApi;
import com.jfms.aaa.model.ActivationCodeRequest;
import com.jfms.aaa.model.UserActivationRequest;
import com.jfms.aaa.model.UserActivationResponse;
import com.jfms.aaa.model.UserRegistrationRequest;
import com.jfms.aaa.service.ActivationService;
import com.jfms.aaa.service.TooRequestException;
import com.jfms.aaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
//@RequestMapping(value = "/aaa/user", produces = "application/json", consumes = "application/json")
public class UserApiImpl implements UserApi{

    @Autowired
    private UserService userService;
    @Autowired
    private ActivationService activationService;

//    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody void registerUser(
            @RequestBody UserRegistrationRequest userRegistrationRequest){
        try {
            userService.register(userRegistrationRequest);
        } catch (TooRequestException e) {
            e.printStackTrace();
            throw new TooManyRequestException(e);
        }
    }


//    @RequestMapping(method = RequestMethod.POST, value = "/activate")
    public @ResponseBody UserActivationResponse activateUser(@RequestBody UserActivationRequest userActivationRequest){
        UserActivationResponse token = activationService.activateUser(userActivationRequest);
        return token;
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/activation/code")
    public @ResponseBody void activationCode(
            @RequestBody ActivationCodeRequest activationCodeRequest) {

        try {
            activationService.generateActivationCode(
                    activationCodeRequest.getActivationCodeLength(),
                    activationCodeRequest.getMobileNumber()
            );
        } catch (TooRequestException e) {
            e.printStackTrace();
            throw new TooManyRequestException(e);
        }

    }
//    @RequestMapping(method = RequestMethod.GET, value = "/reactivate")
    public @ResponseBody UserActivationResponse doReactivate(@RequestHeader("auth") String oldToken){
            UserActivationResponse userActivationResponse = activationService.getNewToken(oldToken);
            return userActivationResponse;
    }

}
