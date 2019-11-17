package org.openmore.common.utils;

import java.util.List;

/**
 * Created by michaeltang on 2018/8/29.
 */
public interface ITreeNode {
    String getId();
    String getParentId();
    void addChild(ITreeNode child);
    List<ITreeNode> getChildren();
}