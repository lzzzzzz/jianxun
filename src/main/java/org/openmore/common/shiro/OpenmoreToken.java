package org.openmore.common.shiro;

import org.apache.shiro.authc.*;
import org.openmore.cms.dto.api.GisInfo;
import org.openmore.cms.entity.enums.LoginType;

import java.util.List;

/**
 * Created by michaeltang on 2017/7/30.
 */
public class OpenmoreToken extends UsernamePasswordToken {

    /**
     * 登录类型：
     * STAFF_PASSWORD：STAFF用户名密码登录
     * USER_PASSWORD：USER用户名密码登录
     * WECHAT：微信登录
     */
    private LoginType type;
    /**
     * 客户端传过来的，平均的位置信息
     */
    private GisInfo.Point location = null;

    private String deviceToken;

    public OpenmoreToken(){

    }

    public OpenmoreToken(LoginType type, String username, String password, String deviceToken, List<GisInfo.Point> locationList) {
        super(username, password);
        this.type = type;
        this.deviceToken = deviceToken;
        setLocationList(locationList);
    }

    /**
     * 设置客户端传递过来的位置列表，生成平均的位置点
     *
     * @param locationList
     */
    public void setLocationList(List<GisInfo.Point> locationList) {
        if (locationList == null || locationList.size() == 0) {
            return;
        }
        double totalLng = 0.0;
        double totalLat = 0.0;
        for (GisInfo.Point gis : locationList) {
            totalLat += gis.lat;
            totalLng += gis.lng;
        }
        this.location = new GisInfo.Point();
        this.location.lat = totalLat / locationList.size();
        this.location.lng = totalLng / locationList.size();
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }

    public GisInfo.Point getLocation() {
        return location;
    }

    public void setLocation(GisInfo.Point location) {
        this.location = location;
    }

    public String getDeviceToken() {
        return this.deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

}
