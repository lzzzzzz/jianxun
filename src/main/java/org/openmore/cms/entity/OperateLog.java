package org.openmore.cms.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "operate_log")
public class OperateLog {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 工作人员id
     */
    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 操作人员id
     */
    private String operator;

    /**
     * 日志内容
     */
    private String content;
    /**
     * ip地址
     */
    private String ipAddress;


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
     * 获取工作人员id
     *
     * @return staff_id - 工作人员id
     */
    public String getStaffId() {
        return staffId;
    }

    /**
     * 设置工作人员id
     *
     * @param staffId 工作人员id
     */
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
     * 获取日志内容
     *
     * @return content - 日志内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置日志内容
     *
     * @param content 日志内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}