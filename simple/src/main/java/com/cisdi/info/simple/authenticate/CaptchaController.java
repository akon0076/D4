package com.cisdi.info.simple.authenticate;

import com.cisdi.info.simple.service.verification.ValidateLogonService;
import com.cisdi.info.simple.util.CaptchaHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/Captcha")
@CrossOrigin(allowCredentials = "true")
public class CaptchaController {
    @Autowired
    private CaptchaHelper captchaHelper;
     @Autowired
    private ValidateLogonService validateLogonService;
    @GetMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
            this.captchaHelper.generate(request, response);
    }
    @GetMapping("/hasVertify")
    public boolean hasVertify(HttpServletRequest request,HttpServletResponse response) {
                  String ipAddress=   request.getRemoteAddr();
        Integer counts=this.validateLogonService.getCounts(ipAddress);
        if (counts == null||counts<3) {  //如果已经输入的次数小于3次则不需要验证
            return false;
        }
        else{
        return true;
          }
    }
}
