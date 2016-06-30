package com.mcmanus.fs.web.controllers;

import com.mcmanus.fs.security.model.AuthenticationToken;
import com.mcmanus.fs.security.model.Login;
import com.mcmanus.fs.security.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authSrv;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationToken login(@RequestBody Login login) {
        return authSrv.login(login);
    }

}
