package com.cisdi.info.simple.controller.organization;


import com.cisdi.info.simple.entity.organization.Employee;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simple/test/xiang")
@CrossOrigin(allowCredentials = "true")
public class ddd {

    @GetMapping("/findEmployee")
    public Employee findEmployee(@RequestParam Long employeeId)
    {
     System.out.println("ddd");
     return null;
    }

}
