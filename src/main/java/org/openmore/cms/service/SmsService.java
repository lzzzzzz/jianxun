package org.openmore.cms.service;

import org.openmore.cms.dto.sms.YunPianBatchResponse;
import org.openmore.cms.dto.sms.YunPianResponse;
import org.openmore.cms.dto.sms.YunPianTemplateResponse;

import java.util.List;

/**
 * @program: CommuniteVisit
 * @description: 云片短信服务类
 * @author: lz
 * @create: 2018-09-07 10:39
 **/
public interface SmsService {
    /**
     * 智能匹配模板接口发短信
     *
     * @param jsonParam   　短信参数内容
     * @param tempCode   　模板id
     * @param mobile 　接受的手机号
     * @return json格式字符串
     */

    String sendSms(String jsonParam, String tempCode, String mobile);
}
