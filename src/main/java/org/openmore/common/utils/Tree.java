package org.openmore.common.utils;

import org.openmore.common.exception.OpenmoreException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by michaeltang on 2018/8/29.
 */
public class Tree {
    private static HashMap<String, ITreeNode> treeNodesMap = null;
    private static List<ITreeNode> treeNodesList = null;

    /**
     * 直接创建工厂对象
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Tree create(List<T> list){
        if(list == null || list.size() == 0) {
            return null;
        }
        return new Tree(list);
    }

    private <T> Tree(List<T> list){
        T t = list.get(0);
        if(t instanceof ITreeNode) {
            List<ITreeNode> treeList = new ArrayList<>();
            for (T tt : list) {
                treeList.add((ITreeNode) tt);
            }
            initTreeNodeMap(treeList);
            initTreeNodeList();
        } else {
            throw new OpenmoreException(4000, "树类型不正确");
        }
    }

    private static void initTreeNodeMap(List<ITreeNode> list){
        ITreeNode treeNode = null;
        treeNodesMap = new HashMap<>();
//          将每个节点的id作为key，初始化map
        for (ITreeNode item : list){
//                treeNode = new TreeNode(item);
            treeNodesMap.put(item.getId(), item);
        }

        Iterator<ITreeNode> iter = treeNodesMap.values().iterator();
        ITreeNode parentTreeNode = null;
        while (iter.hasNext()){
            treeNode = iter.next();
//              父节点不处理
            if (StringUtils.isEmpty(treeNode.getParentId())){
                continue;
            }
//              如果有父节点，将自己添加到父节点chidren里
            parentTreeNode = treeNodesMap.get(treeNode.getParentId());
            if (parentTreeNode != null){
                parentTreeNode.addChild(treeNode);
            }
        }
    }

    private static void initTreeNodeList(){
        treeNodesList = new ArrayList<>();
        if (treeNodesMap.size() == 0){
            return;
        }
        Iterator<ITreeNode> iter = treeNodesMap.values().iterator();
        ITreeNode treeNode = null;
        while (iter.hasNext()){
            treeNode = iter.next();
//              找到根节点们
            if (StringUtils.isEmpty(treeNode.getParentId())){
                treeNodesList.add(treeNode);
                if(treeNode.getChildren() != null) {
//                        treeNodesList.addAll(treeNode.getChildren());
                }
            }
        }
    }

    public static <T> List<T> getTree() {
        List<T> result = new ArrayList<>();
        for (ITreeNode node : treeNodesList) {
            result.add((T)node);
        }
        return result;
    }

    public static <T> List<T> getRoot() {
        List<T> rootList = new ArrayList<>();
        if (treeNodesList.size() > 0) {
            for (ITreeNode node : treeNodesList) {
                if (StringUtils.isEmpty(node.getParentId()))
                    rootList.add((T)node);
            }
        }
        return rootList;
    }

    public static ITreeNode getTreeNode(String nodeId) {
        return treeNodesMap.get(nodeId);
    }
}
