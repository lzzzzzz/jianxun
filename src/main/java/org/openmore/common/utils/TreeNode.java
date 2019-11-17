package org.openmore.common.utils;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by michaeltang on 2018/8/29.
 */
public class TreeNode implements ITreeNode{
    @ApiModelProperty(value = "节点id")
    private String id;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "孩子节点")
    private List<ITreeNode> children;


    public TreeNode(){

    }

    public TreeNode(ITreeNode copy) {
        BeanUtils.copyProperties(copy, this);
    }

    @Override
    public void addChild(ITreeNode child) {
        children.add(child);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<ITreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
    }
}
