package com.cisdi.info.simple.util;

import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author gjt
 * @version 1.0
 * @className
 * @date 2018/12/20 15:37
 * @description
 */
public class FilterUtils {

    /**
     * filter as List
     * This method filters a ListMap based on the incoming conditions
     * Use the JAVA8 feature filter to filter
     * 这个方法能根据传入的条件将一个ListMap进行过滤
     * 利用了JAVA8的特性filter来过滤
     * @param maps 需要过滤的数据集合（Data sets that need to be filtered）
     * @param condition 过滤条件，注意使用java8的lambda语法表示 （pay attention to the lambda grammar representation in Java 8）
     * @return 过滤结果 filterResult
     */
    private static List<Map<String , Object>> filterAsList(List<Map<String , Object>> maps ,Predicate condition) {

        List<Map<String , Object>> maps1 = (List<Map<String, Object>>) maps
                .stream()
                .filter(condition)
                .collect(Collectors.toList());

        return maps1;
    }

//    public static void main(String[] args) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("name", "ZK");
//        map.put("age", 13);
//        map.put("学年度考核综合得分", 81.1d);
//
//        Map<String, Object> map2 = new LinkedHashMap<String, Object>();
//        map2.put("name", "ZA");
//        map2.put("age", 15);
//        map2.put("学年度考核综合得分", 81.5d);
//
//        Map<String, Object> map3 = new LinkedHashMap<String, Object>();
//        map3.put("name", "CX");
//        map3.put("age", 20);
//        map3.put("学年度考核综合得分", 80.5d);
//
//        Map<String, Object> map4 = new LinkedHashMap<String, Object>();
//        map4.put("name", "CX");
//        map4.put("age", 18);
//        map4.put("学年度考核综合得分", 80.5d);
//
//        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//        list.add(map);
//        list.add(map2);
//        list.add(map3);
//        list.add(map4);
//        Object o = new Object();
//
//        Predicate condition = age -> (int)list.get(1).get("age") > 13;
//        List<Map<String , Object>> maps1 = filterAsList(list ,condition);
//
//        for (Map<String, Object> m : maps1) {
//            System.out.println(m.get("name") + " " + m.get("学年度考核综合得分") + " " + m.get("age"));
//        }
//    }

}
