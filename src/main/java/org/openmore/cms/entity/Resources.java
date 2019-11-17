package org.openmore.cms.entity;

import org.openmore.cms.entity.enums.ResourceType;

import java.util.Date;
import javax.persistence.*;

/**阿里云文件或保利视频信息保存*/
@Table(name = "resources")
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 阿里云OSS保存key
     */
    @Column(name="oss_key")
    private String key;

    /**
     * 文件类型
     */
    @Column(name="resource_type")
    private ResourceType type;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
    */
    private String size;

    /**时长*/
    private String duration;

    /**
     * 文件访问url
     */
    private String url;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
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
     * 获取阿里云OSS保存key
     *
     * @return key - 阿里云OSS保存key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置阿里云OSS保存key
     *
     * @param key 阿里云OSS保存key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取文件类型
     *
     * @return type - 文件类型
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * 设置文件类型
     *
     * @param type 文件类型
     */
    public void setType(ResourceType type) {
        this.type = type;
    }

    /**
     * 获取文件名称
     *
     * @return name - 文件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名称
     *
     * @param name 文件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取文件访问url
     *
     * @return url - 文件访问url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置文件访问url
     *
     * @param url 文件访问url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取更新时间
     *
     * @return updated_time - 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置更新时间
     *
     * @param updatedTime 更新时间
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}