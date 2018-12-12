package com.ddd.dev.generator;

import org.apache.commons.lang3.StringUtils;

public class Test1 {

    public static void main(String[] args)
    {
        String fieldName = "EId";
        String columnName = StringUtils.lowerCase(StringUtils.join( StringUtils.splitByCharacterTypeCamelCase(fieldName),"/"));
        System.out.println(columnName);
    }
}
