package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.openmore.cms.entity.User;
import org.openmore.common.annotation.ReferencedField;


@ApiModel("Feedback显示模型")
public class FeedbackDto {
    @ApiModelProperty(value = "id")
    public String id;
    @ApiModelProperty(value = "用户ID")
    public String userId;
    @ApiModelProperty(value = "用户信息")
    @ReferencedField(refClass = User.class, targetClass = UserDto.class, conditionalField = "id", localField = "userId")
    public UserDto user;
    @ApiModelProperty(value = "反馈内容")
    public String content;
    @ApiModelProperty(value = "联系方式")
    private String contact;;
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdTime;
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updatedTime;
    @ApiModelProperty(value = "删除状态")
    public boolean deleted;
    @ApiModelProperty(value = "乐观锁")
    public int version;
    @ApiModelProperty(value = "操作人员")
    public String operator;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return this.userId;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public Date getCreatedTime(){
        return this.createdTime;
    }
    public void setUpdatedTime(Date updatedTime){
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime(){
        return this.updatedTime;
    }
    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public boolean getDeleted(){
        return this.deleted;
    }
    public void setVersion(int version){
        this.version = version;
    }

    public int getVersion(){
        return this.version;
    }
    public void setOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return this.operator;
    }

    public String getContact() {
        return contact;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}