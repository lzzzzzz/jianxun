package org.openmore.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.StringUtils;
import org.openmore.cms.dto.SMSDto;
import org.openmore.cms.service.SmsService;
import org.openmore.common.exception.OpenmoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @program: CommuniteVisit
 * @description: 短信服务实现类 调用此类的方法的地方需要判断是否需要匹配社区短信模版（tenantId不为空）,若社区id为空则使用系统默认模版
 * @author: lz
 * @create: 2018-09-07 10:44
 **/
@Service
@PropertySource("classpath:application.properties")
public class SmsServiceImpl extends BaseServiceImpl implements SmsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    /**付款通知*/
    public static final String SMS_PAY_NOTIFY="SMS_167051055";
    /**短信验证码*/
    public static final String SMS_SEND_CODE="SMS_167051035";


    /**短信验证码模版*/
    //public static final String MODEL_SEND_MESSAGE= "SMS_165692675";
    /**
     * 智能匹配发送单条短信
     */
    @Override
    public String sendSms(String jsonParams, String tempCode, String mobile) {
        logger.info("==>send sms to mobile:" + mobile);
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "趣平方");
        request.putQueryParameter("TemplateCode", tempCode);
        if(!StringUtils.isEmpty(jsonParams)) {
            request.putQueryParameter("TemplateParam", jsonParams);
        }

        try {
            CommonResponse response = client.getCommonResponse(request);
            //if(response.getData().)
            //System.out.println(JSON.toJSONString(response));
            SMSDto smsDto = JSON.parseObject(response.getData(), SMSDto.class);
            //超出限制发送频率
            if(smsDto.getCode().equals("isv.BUSINESS_LIMIT_CONTROL")){
                throw new OpenmoreException(400, "请勿频繁发送");
            }
            return smsDto.getMessage();
        } catch (ServerException e) {
            //e.printStackTrace();
            logger.error(e.getMessage());
            return e.getMessage();
        } catch (ClientException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }
}
