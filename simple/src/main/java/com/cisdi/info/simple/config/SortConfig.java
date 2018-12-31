package com.cisdi.info.simple.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author gjt
 * @version 1.0
 * @className SortConfig
 * @date 2018/12/17 22:25
 * @description 排序配置
 */
public class SortConfig {
    /**
     * 长度
     */
    private Integer size;
    /**
     * 参数
     */
    private List<String> variables;
    /**
     * 传入排序方法，以lamda表达式的方式进行
     * @key 方法
     * @value 排序顺序 up or down
     */
    private Map<Function<LinkedHashMap<String, Object>,Object> ,String> functions;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public Map<Function<LinkedHashMap<String,Object>,Object>, String> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<Function<LinkedHashMap<String, Object>,Object> ,String> functions) {
        this.functions = functions;
    }

    /**
     * 升序
     * @return
     */
    public String getUp(){
        return "up";
    }

    /**
     * 降序
     * @return
     */
    public String getDown() {
        return "down";
    }
}
