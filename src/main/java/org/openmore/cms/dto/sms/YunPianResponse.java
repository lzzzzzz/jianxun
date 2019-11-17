package org.openmore.cms.dto.sms;

/**
 * 云片单条短信短信发送返回数据结构
 *
 * @see https://www.yunpian.com/api2.0/api-recode.html
 */
public class YunPianResponse {

    /**
     * 0代表发送成功，其他code代表出错
     */
    private int code;
    /**
     * 例如""发送成功""，或者相应错误信息
     */
    private String msg;
    /**
     * 发送成功短信的计费条数(计费条数：70个字一条，超出70个字时按每67字一条计费)
     */
    private Integer count;

    /**
     * 扣费金额，单位：元，类型：双精度浮点型/double
     */
    private Float fee;
    /**
     * 计费单位；例如：“RMB”
     */
    private String unit;
    /**
     * 发送手机号
     */
    private String mobile;
    /**
     * 短信id，64位整型， 对应Java和C#的long，不可用int解析
     */
    private String sid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}