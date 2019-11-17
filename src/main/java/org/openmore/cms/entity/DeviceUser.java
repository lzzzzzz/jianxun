package org.openmore.cms.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "device_user")
public class DeviceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 是否是VIP
     */
    @Column(name = "is_vip")
    private Boolean isVip;

    @Column(name = "device_token")
    private String deviceToken;

    /**
     * VIP过期时间
     */
    @Column(name = "vip_expired_time")
    private Date vipExpiredTime;

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
     * 获取是否是VIP
     *
     * @return is_vip - 是否是VIP
     */
    public Boolean getIsVip() {
        return isVip;
    }

    /**
     * 设置是否是VIP
     *
     * @param isVip 是否是VIP
     */
    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    /**
     * @return device_token
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * @param deviceToken
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * 获取VIP过期时间
     *
     * @return vip_expired_time - VIP过期时间
     */
    public Date getVipExpiredTime() {
        return vipExpiredTime;
    }

    /**
     * 设置VIP过期时间
     *
     * @param vipExpiredTime VIP过期时间
     */
    public void setVipExpiredTime(Date vipExpiredTime) {
        this.vipExpiredTime = vipExpiredTime;
    }
}