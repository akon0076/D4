package com.cisdi.info.simple.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.stereotype.Component;

@Component
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString(){
        return" ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
