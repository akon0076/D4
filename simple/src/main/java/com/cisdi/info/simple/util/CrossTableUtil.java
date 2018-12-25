package com.cisdi.info.simple.util;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dto.crossTable.CrossTableDTO;
import com.cisdi.info.simple.entity.crossTable.CrossFieldDef;
import com.cisdi.info.simple.entity.crossTable.CrossTableConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * @author gjt
 * @version 1.0
 * @className CrossTableUtil
 * @date 2018/12/6 14:59
 * @description
 */
public class CrossTableUtil {

    private static Logger logger = LogManager.getLogger();

    /**
     *  交叉表转成正常表格
     * @param rowList 从数据库得出来的数据
     * @param crossTableConfig
     * @return
     * @throws IllegalAccessException
     */
    public static CrossTableDTO toCrossTableByObject(List<Object> rowList, CrossTableConfig crossTableConfig ,boolean isSplicingHeader){
        if (rowList == null || crossTableConfig == null) {
            logger.error("行数据或配置不能为空");
            throw new DDDException("行数据或配置不能为空");
        }
        CrossTableDTO crossTableDTO = new CrossTableDTO();
        List<Map> rows = D4Util.objectToListMap(rowList);
        List<Map<String ,Object>> res = getRowListData(rows ,crossTableConfig);
        // 是否需要内部拼接交叉表头
        if (isSplicingHeader) {
            List<CrossFieldDef> crossFieldDefs = spliceHeader(crossTableConfig ,res);
            crossTableConfig.setCrossFieldDefs(crossFieldDefs);
        }
        crossTableDTO.setData(res);
        crossTableDTO.setCrossTableConfig(crossTableConfig);
        return crossTableDTO;
    }


    /**
     *  交叉表转成正常表格
     * @param rowList 从数据库得出来的数据
     * @param crossTableConfig
     * @param isSplicingHeader 是否自己拼接
     * @return
     * @throws IllegalAccessException
     */
    public static CrossTableDTO toCrossTableByMap(List<Map> rowList, CrossTableConfig crossTableConfig ,boolean isSplicingHeader){
        if (rowList == null || crossTableConfig == null) {
            logger.error("行数据或配置不能为空");
            throw new DDDException("行数据或配置不能为空");
        }
        CrossTableDTO crossTableDTO = new CrossTableDTO();
        List<Map<String ,Object>> res = getRowListData(rowList ,crossTableConfig);
        // 是否需要内部拼接交叉表头
        if (isSplicingHeader) {
            List<CrossFieldDef> crossFieldDefs = spliceHeader(crossTableConfig , res);
            crossTableConfig.setCrossFieldDefs(crossFieldDefs);
        }
        crossTableDTO.setData(res);
        crossTableDTO.setCrossTableConfig(crossTableConfig);
        return crossTableDTO;
    }


    /**
     * 判断从数据库中得出的数据里面是否存在有相同的交叉表主键的数据
     * @param res 已经存好的数据
     * @param crossKey  交叉组件的字段
     * @param resMap  待处理数据
     * @return 0 不存在相同的
     *          index 存在相同的这是位置。
     */
    private static Integer isHaveSameCrossKeyIndex(List<Map<String, Object>> res, List<String> crossKey, Map<String, Object> resMap) {

        // 还未处理数据
        if (res.size() == 0 || crossKey.size() == 0 || resMap.size() == 0) {
            logger.error("行数据或配置不能为空");
            return -1;
        }

        List<String> resMapCrossKeyData = new ArrayList<>();

        for (int j = 0; j < crossKey.size(); j++) {
            resMapCrossKeyData.add(String.valueOf(resMap.get(crossKey.get(j))));
        }
        for (int i = 0; i < res.size(); i++) {
            int flag = 0;
            // 提取出对应行的数据，找到crosskey对应的value数据组装成一个List
            Map<String ,Object> resData = res.get(i);
            List<String> resCrossKeyData = new ArrayList<>();
            for (int j = 0; j < crossKey.size(); j++) {
                resCrossKeyData.add(String.valueOf(resData.get(crossKey.get(j))));
            }
            for (int j = 0; j < resCrossKeyData.size(); j++) {
                for (int k = 0; k < resMapCrossKeyData.size(); k++) {
                    // 无论crossKey有多少，只要其中一个相同，就代表这两条数据的crossKey相同
                    if (resCrossKeyData.get(j).equals(resMapCrossKeyData.get(k))) {
                        flag++;
                    }
                }
            }
            if (flag == crossKey.size()) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 处理从数据库查回来的数据，将需要交叉的数据转化成一行一行的数据
     * @param rowList 传输回来的行数据
     * @param crossTableConfig 交叉表配置
     * @return 将结果处理成一个List<Map>
     */
    private static List<Map<String ,Object>>getRowListData(List<Map> rowList, CrossTableConfig crossTableConfig) {
        List<Map<String ,Object>> res = new ArrayList<>();
        for (int i = 0; i < rowList.size(); i++) {
            Map<String ,Object> resData = rowList.get(i);
            Map<String ,Object> res1 = new LinkedHashMap<>();
            // 获取交叉表指定字段
            Map<String ,String> crossFields = crossTableConfig.getCrossFields();
            List<String> crossKey = crossTableConfig.getCrossKeys();
            List<String> rowFields = crossTableConfig.getRowFields();
            // 是否存在相同的crosskey，如果是，则返回对应crosskey所在的索引位置，如果不是则返回-1。
            Integer theSameCrossKeyIndex = isHaveSameCrossKeyIndex(res ,crossKey ,resData);
            if ( theSameCrossKeyIndex ==  -1) {
                // 拼接的crossKey
                for (int j = 0; j < crossKey.size(); j++) {
                    res1.put(crossKey.get(j) ,resData.get(crossKey.get(j)));
                }
                for (int j = 0; j < rowFields.size(); j++) {
                    res1.put(rowFields.get(j) ,resData.get(rowFields.get(j)));
                }
            }
            // 取出项目key和value的key，然后再从resData中取出对应的参数
            for(Map.Entry<String, String> key1 : crossFields.entrySet()) {
                String projectKey = key1.getKey();
                String projectValue = key1.getValue();
                res1.put(String.valueOf(resData.get(projectKey)) ,resData.get(projectValue));
            }
            if (theSameCrossKeyIndex >= 0) {
                // 取出数据
                Map<String ,Object> data = res.get(theSameCrossKeyIndex);
                res.remove(data);
                // 取出项目key和value的key，然后再从resData中取出对应的参数
                for(Map.Entry<String, Object> key : res1.entrySet()) {
                    String projectKey = key.getKey();
                    Object projectValue = key.getValue();
                    data.put(projectKey ,projectValue);
                }
                res.add(data);
            } else {
                res.add(res1);
            }
        }
        return res;
    }

    /**
     * 如果需要行转列过程中自己拼接表头的可以调用此方法
     *
     * @param rows 基础配置
     * @param res 已经转换好的数据
     * @return
     */
    private static List<CrossFieldDef> spliceHeader(CrossTableConfig rows, List<Map<String, Object>> res) {
        List<CrossFieldDef> crossFieldDefs = rows.getCrossFieldDefs() == null ? new ArrayList<>() : rows.getCrossFieldDefs();
        Map<String ,Object> value = res.get(0);
        for(Map.Entry<String, Object> key : value.entrySet()) {
            String projectKey = key.getKey();
            CrossFieldDef crossFieldDef = new CrossFieldDef();
            // 是否是交叉表常规字段
            boolean isRowFields = false;
            // 是否是交叉表主键
            boolean isCrossKeys = false;
            // 判断是否是交叉表常规字段
            for (int i = 0; i < rows.getRowFields().size(); i++) {
                if(rows.getRowFields().get(i).equals(projectKey)) {
                    isRowFields = true;
                }
            }
            // 判断是否是交叉表主键
            for (int i = 0; i < rows.getCrossKeys().size(); i++) {
                if(rows.getCrossKeys().get(i).equals(projectKey)) {
                    isCrossKeys = true;
                }
            }
            // 如果都不是，则设置为交叉表数据
            if (!(isRowFields || isCrossKeys)) {
                crossFieldDef.setName(projectKey);
                crossFieldDef.setLabel(projectKey);
                crossFieldDef.setCrossField();
                crossFieldDefs.add(crossFieldDef);
            }
        }
        return crossFieldDefs;
    }

}
