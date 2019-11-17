package org.openmore.cms.entity;

import org.openmore.cms.entity.enums.ResourceForeignType;

import java.util.Date;
import javax.persistence.*;

@Table(name = "foreign_resource")
public class ForeignResource {
    @Column(name = "resource_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String resourceId;

    @Column(name = "foreign_id")
    private String foreignId;

    @Column(name = "foreign_type")
    private ResourceForeignType foreignType;

    private String params;

    @Column(name = "created_time")
    private Date createdTime;

    private String operator;

    private String id;

    /**
     * @return resource_id
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return foreign_id
     */
    public String getForeignId() {
        return foreignId;
    }

    /**
     * @param foreignId
     */
    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    /**
     * @return foreign_type
     */
    public ResourceForeignType getForeignType() {
        return foreignType;
    }

    /**
     * @param foreignType
     */
    public void setForeignType(ResourceForeignType foreignType) {
        this.foreignType = foreignType;
    }

    /**
     * @return params
     */
    public String getParams() {
        return params;
    }

    /**
     * @param params
     */
    public void setParams(String params) {
        this.params = params;
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
     * @return operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

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
}