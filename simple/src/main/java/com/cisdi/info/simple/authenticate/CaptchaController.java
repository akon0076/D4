package com.cisdi.info.simple.authenticate;

import com.cisdi.info.simple.util.CaptchaHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Captcha")
@CrossOrigin(allowCredentials = "true")
public class CaptchaController {
    @Autowired
    private CaptchaHelper captchaHelper;
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request,HttpServletResponse response) {
        this.captchaHelper.generate(request, response);
    }
}
