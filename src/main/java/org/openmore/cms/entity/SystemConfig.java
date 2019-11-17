package org.openmore.cms.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "system_config")
public class SystemConfig {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 配置名
     */
    private String name;

    /**
     * 配置内容
     */
    private String value;

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
     * 操作人员id
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取配置名
     *
     * @return name - 配置名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置配置名
     *
     * @param name 配置名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取配置内容
     *
     * @return value - 配置内容
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置配置内容
     *
     * @param value 配置内容
     */
    public void setValue(String value) {
        this.value = value;
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

    /**
     * 设置操作人员id
     *
     * @param operator 操作人员id
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}