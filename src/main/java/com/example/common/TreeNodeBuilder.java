package com.example.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: project-demo
 * @Package: com.example.common
 * @ClassName: TreeNodeBuilder
 * @Author: 游佳琪
 * @Description: 树节点构造
 * @Date: 2020-8-15 21:39
 * @Version: 1.0
 */
public class TreeNodeBuilder {
    /**
     *          把没有层级关系的集合变成有层级关系的 ，跟据数据信息
     * @param treeNodes
     * @param topPid
     * @return
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes, Integer topPid){
        List<TreeNode> nodes=new ArrayList<>();
//        使用双重循环方式构建树节点导航菜单栏
        for (TreeNode n1 : treeNodes) {
//            第一次循环寻找根节点下的子节点
            if(n1.getPid()==topPid) {
                nodes.add(n1);
            }
            for (TreeNode n2 : treeNodes) {
//                第二次循环：寻找根节点下的子节点下的子节点
                if(n1.getId()==n2.getPid()) {
                    n1.getChildren().add(n2);
                }
            }
        }
        return nodes;
    }
}
