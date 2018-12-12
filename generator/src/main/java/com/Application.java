package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * @author CISDI
 * @date 2018/04/27
 */
@SpringBootApplication(scanBasePackages = {"com.cisdi.info.pp.*","com.cisdi.info.ps.*","com.cisdi.info.pm.*","com.cisdi.info.simple.*","com.ddd.dev.*"}, exclude = {SecurityAutoConfiguration.class})
@EntityScan("com.cisdi.info.pm.*")
@EnableDiscoveryClient(autoRegister = false)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
