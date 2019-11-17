package org.openmore.cms.entity;

import org.openmore.cms.entity.enums.AppType;

import java.util.Date;
import javax.persistence.*;

@Table(name = "app_version")
public class AppVersion {
    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD
     */
    @Column(name="type")
    private AppType type;

    /**
     * 版本号
     */
    @Column(name = "version_code")
    private String versionCode;

    /**
     * 版本名
     */
    @Column(name = "version_name")
    private String versionName;

    /**
     * 下载地址
     */
    private String url;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "need_update")
    private Boolean needUpdate;
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
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD
     *
     * @return type - app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD
     */
    public AppType getType() {
        return type;
    }

    /**
     * 设置app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD
     *
     * @param type app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD
     */
    public void setType(AppType type) {
        this.type = type;
    }

    /**
     * 获取TYPE_HOME_PAGE
     *
     * @return version_code - TYPE_HOME_PAGE
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * 设置TYPE_HOME_PAGE
     *
     * @param versionCode TYPE_HOME_PAGE
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * 获取用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN
     *
     * @return version_name - 用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 设置用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN
     *
     * @param versionName 用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * 获取下载地址
     *
     * @return url - 下载地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置下载地址
     *
     * @param url 下载地址
     */
    public void setUrl(String url) {
        this.url = url;
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
     * 获取操作人员
     *
     * @return operator - 操作人员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人员
     *
     * @param operator 操作人员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Boolean getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(Boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}