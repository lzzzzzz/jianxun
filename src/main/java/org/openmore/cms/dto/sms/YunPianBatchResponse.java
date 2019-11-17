package org.openmore.cms.dto.sms;

import java.util.List;

/**
 * @program: CommuniteVisit
 * @description: 云片批量发送短信返回数据结构
 * @author: lz
 * @create: 2018-09-07 11:08
 **/
public class YunPianBatchResponse {

    /**
     * 扣费条数，70个字一条，超出70个字时按每67字一条计
     */
    private Integer total_count;

    /**
     * 扣费金额，单位：元，类型：双精度浮点型/double
     */
    private String total_fee;

    /**
     * 计费单位；例如：“RMB”
     */
    private String unit;

    /**
     * 参考单个短信发送返回的数据
     */
    private List<YunPianResponse> data;

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<YunPianResponse> getData() {
        return data;
    }

    public void setData(List<YunPianResponse> data) {
        this.data = data;
    }
}
