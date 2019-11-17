package org.openmore.cms.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dictionary")
public class Dictionary {

    /**
     * 词典名，词典值的拼音
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String dicKey;

    /**
     * 词典父id
     */
    @Column(name = "parent_key")
    private String parentKey;

    /**
     * 词典值最多8个汉字
     */
    private String name;
    /**
     * 英文名字
     */
    @Column(name = "en_name")
    private String enName;
    /**
     * 备注
     */
    @Column(name = "data")
    private String data;


    /**
     * 排序
     */
    @Column(name = "sort_order")
    private Integer sortOrder;


    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    /**
     * 删除状态
     */
    private Boolean deleted;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 获取词典名，词典值的拼音
     *
     * @return key - 词典名，词典值的拼音
     */
    public String getDicKey() {
        return dicKey;
    }

    /**
     * 设置词典名，词典值的拼音
     *
     * @param dicKey 词典名，词典值的拼音
     */
    public void setDicKey(String dicKey) {
        this.dicKey = dicKey;
    }

    /**
     * 获取词典父id
     *
     * @return parent_key - 词典父id
     */
    public String getParentKey() {
        return parentKey;
    }

    /**
     * 设置词典父id
     *
     * @param parentKey 词典父id
     */
    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    /**
     * 获取词典值最多8个汉字
     *
     * @return name - 词典值最多8个汉字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置词典值最多8个汉字
     *
     * @param name 词典值最多8个汉字
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return created_time
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return updated_time
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 获取删除状态
     *
     * @return deleted - 删除状态
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 设置删除状态
     *
     * @param deleted 删除状态
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取乐观锁
     *
     * @return version - 乐观锁
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置乐观锁
     *
     * @param version 乐观锁
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取操作人员id
     *
     * @return operator - 操作人员id
     */
    public String getOperator() {
        return operator;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 设置操作人员id
     *
     * @param operator 操作人员id
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}