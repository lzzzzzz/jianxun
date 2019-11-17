package org.openmore.cms.dto.sms;

/**
 * @program: CommuniteVisit
 * @description: 云片模板操作返回数据类型
 * @author: lz
 * @create: 2018-09-07 11:11
 **/
public class YunPianTemplateResponse {

    /**
     * 模板id
     */
    private String tpl_id;

    /**
     * 模板内容
     */
    private String tpl_content;

    /**
     * 审核状态：CHECKING/SUCCESS/FAIL
     */
    private String check_status;

    /**
     * 审核未通过的原因
     */
    private String reason;

    public String getTpl_id() {
        return tpl_id;
    }

    public void setTpl_id(String tpl_id) {
        this.tpl_id = tpl_id;
    }

    public String getTpl_content() {
        return tpl_content;
    }

    public void setTpl_content(String tpl_content) {
        this.tpl_content = tpl_content;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
