package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author CISDI
 * @date 2018/04/27
 */
@SpringBootApplication(scanBasePackages = {"com.cisdi.info.simple.*","com.ddd.dev.*"}, exclude = {SecurityAutoConfiguration.class})
@EnableDiscoveryClient(autoRegister = false)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
