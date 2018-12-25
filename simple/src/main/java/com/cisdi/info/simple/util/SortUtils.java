package com.cisdi.info.simple.util;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.config.SortConfig;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * @author gjt
 * @version 1.0
 * @className SortUtils
 * @date 2018/12/17 22:03
 * @description
 */
public class SortUtils {

    /**
     * 排序算法
     *
     * @param sortConfig 排序配置
     * @return
     */
    private static Comparator initComparator(SortConfig sortConfig ,List<Map<String, Object>> data) {
        Map<Function<LinkedHashMap<String, Object>,Object> ,String> functions = sortConfig.getFunctions();
        Comparator comparator = null;
        int i = 0;
        //遍历map中的键
        for (Function<LinkedHashMap<String, Object>,Object> key : functions.keySet()) {

            Map<String, Object> res = data.get(0);
            LinkedHashMap<String, Object> res1 =  new LinkedHashMap<>();
            res1.putAll(res);
            Object data1 =   key.apply(res1);

            Function key1 = key;
            boolean isUp = "up".equals(functions.get(key1));
            boolean isDown = "down".equals(functions.get(key1));
            if (i == 0) {
                if (isUp) {
                    if (data1 instanceof Double) {
                        comparator = comparingDouble(key1);
                    } else {
                        comparator = Comparator.comparing(key1);
                    }
                }else if(isDown){
                    if (data1 instanceof Double) {
                        comparator = comparingDouble(key1).reversed();
                    } else {
                        comparator = Comparator.comparing(key1).reversed();
                    }
                } else {
                    throw new DDDException("传入的type类型不合法");
                }
            }else {
                if (isUp) {
                    if (data1 instanceof Double) {
                        comparator = comparator.thenComparing(comparingDouble(key1));
                    } else {
                        comparator = comparator.thenComparing(Comparator.comparing(key1));
                    }

                }else if(isDown){
                    if (data1 instanceof Double) {
                        comparator = comparator.thenComparing(comparingDouble(key1).reversed());
                    } else {
                        comparator = comparator.thenComparing(Comparator.comparing(key1).reversed());
                    }
                } else {
                    throw new DDDException("传入的type类型不合法");
                }
            }
            i++;
        }
        return comparator;

    }
    /**
     * 排序算法
     * @param sortConfig 排序配置
     * @param data 排序数据
     * @return
     */
    public static List<Map<String, Object>> sortMap(SortConfig sortConfig ,List<Map<String, Object>> data ) {

        Comparator comparable = initComparator(sortConfig ,data);
        // 排序代码如下
        List<Map<String, Object>> collect = (List<Map<String, Object>>)data
                .stream()
                .sorted(comparable)
                .collect(Collectors.toList());

        return collect;
    }

    /**
     * double类型排序算法
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static<T> Comparator<T> comparingDouble(Function keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable)
                (c1, c2) -> {
                    return Double.compare(Double.parseDouble(keyExtractor.apply(c1).toString()), Double.parseDouble(keyExtractor.apply(c2).toString()));
                };
    }
}
