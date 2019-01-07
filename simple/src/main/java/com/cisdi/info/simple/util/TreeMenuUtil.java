package com.cisdi.info.simple.util;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.config.SelectTreeConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gjt
 * @version 1.0
 * @className 树构造工具类
 * @date 2018/12/3 22:12
 * @description
 */
public class TreeMenuUtil {

    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String add = "1";

        TreeNode<String> treeNode = new TreeNode<String>(add ,add, null);
    }

    /**
     * 初始化树
     * @param datas 数据集合
     * @param selectTreeConfig 关键字
     */
    public static HashMap<String ,TreeNode> toTree(List<Object> datas , SelectTreeConfig selectTreeConfig) {

        boolean isNull = (datas.size() == 0) && selectTreeConfig == null;
        if (isNull) {
            logger.error("传入数据不能为空");
           throw new DDDException("传入数据不能为空");
        }
        // 初始化树根
        TreeNode<Object> tree = new TreeNode<>(selectTreeConfig.getLabel() ,selectTreeConfig.getTreeName() , null);

        List<Map> data = D4Util.objectToListMap(datas);

        for (int i = 0; i < datas.size() ;i++) {
            TreeNode<Object> parentCode = new TreeNode<>();
            parentCode.setLabel(selectTreeConfig.getLabel());
            parentCode.setValue(data.get(i).get(selectTreeConfig.getParentCode()));
//            TreeNode<String> code = new TreeNode<>(selectTreeConfig.getLabel() ,data.get(i).get(selectTreeConfig.getCode()) ,parentCode);
            // 第一个
            TreeNode<Object> treeNode = isParentCode(tree ,data.get(i).get(selectTreeConfig.getParentCode()));
            if (i == 0 || treeNode == null) {
                parentCode.setParentNode(tree);
            }
            if (treeNode != null){
                parentCode.setParentNode(treeNode);
            }

        }
        return null;
    }
    /**
     *
     * @param tree
     * @param parentCode
     * @return
     */
    private static TreeNode<Object> isParentCode(TreeNode<Object> tree , Object parentCode) {

        if (tree.getValue().equals(parentCode)) {
            return tree;
        }
        if (tree != null) {
            isParentCode(tree.getParentNode() ,parentCode);
        }else {
            return null;
        }
        return null;
    }
    /**
     * 树
     * @param <T>
     */
     static class TreeNode<T> {
        /**
         * 结点
         */
        private T value;
        /**
         * 标签
         */
        private T label;
        /**
         * 父节点
         */
        private TreeNode<T> parentNode;
        /**
         * 树的深度
         */
        private Integer size;

        TreeNode (T label ,T value ,TreeNode<T> parentNode){
            this.label = label;
            this.parentNode = parentNode;
            this.value = value;
            size++;
        }
        TreeNode(){}

        /**
         * return the tree size；
         * @return
         */
        public int getSize(){
            return size;
        }

        /**
         * 得到结点数值
         * @return
         */
        public T getValue() {
            return value;
        }

        /**
         * 设置结点数值
         * @param value
         */
        public void setValue(T value) {
            this.value = value;
        }

        /**
         * 得到标签
         * @return
         */
        public T getLabel() {
            return label;
        }

        /**
         * 设置标签
         * @param label
         */
        public void setLabel(T label) {
            this.label = label;
        }

        /**
         * 得到父级结点
         * @return
         */
        public TreeNode<T> getParentNode() {
            return parentNode;
        }

        /**
         * 设置父级结点
         * @param parentNode
         */
        public void setParentNode(TreeNode<T> parentNode) {
            this.parentNode = parentNode;
        }
    }
}
