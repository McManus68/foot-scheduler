package com.mcmanus.fs.web.controllers;

import com.mcmanus.fs.security.exceptions.InvalidLostTokenException;
import com.mcmanus.fs.security.model.ChangePassword;
import com.mcmanus.fs.security.services.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping(value = "/recovery")
public class RecoveryController {

    @Autowired
    private RecoveryService recoverySrv;

    @RequestMapping(value = "/lost/send", method = RequestMethod.POST)
    public void sendLostPassword(@RequestBody final Map<String, String> params, HttpServletRequest request) throws IOException {
        String urlStr = request.getRequestURL().toString();
        URL url = new URL(urlStr);
        recoverySrv.sendLostPassword(params.get("mail"), urlStr.substring(0, urlStr.length() - url.getPath().length()));
    }

    @RequestMapping(value = "/lost/validate", method = RequestMethod.POST)
    public void validateLostPassword(@RequestBody final Map<String, String> params) throws InvalidLostTokenException {
        recoverySrv.validateLostPasswordToken(params.get("token"));
    }

    @RequestMapping(value = "/lost/change", method = RequestMethod.POST)
    public void changeLostPassword(@RequestBody ChangePassword newPassword) throws InvalidLostTokenException {
        recoverySrv.changeLostPassword(newPassword);
    }
}
