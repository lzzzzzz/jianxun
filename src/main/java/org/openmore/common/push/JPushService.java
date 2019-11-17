package org.openmore.common.push;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Map;

/**
 * java后台极光推送方式一：使用Http API
 * 此种方式需要自定义http请求发送客户端:HttpClient
 */
@Service
public class JPushService {
    private static final Logger log = LoggerFactory.getLogger(JPushService.class);
    private String pushUrl = "https://api.jpush.cn/v3/push";
    //TODO ::上线改为true
    private boolean apns_production = false;
    private int time_to_live = 86400;

    @Value("${JPush.secret}")
    private String masterSecret;
    @Value("${JPush.appKey}")
    private String appKey ;
    @Value("${JPush.Pad.secret}")
    private String masterPadSecret;
    @Value("${JPush.Pad.appKey}")
    private String appPadKey ;
    /**
     * 极光推送
     */
    public void jPush(String alias, JPushMessage jPushMessage){
        try{
            if(null == jPushMessage){
                jPushMessage = new JPushMessage();
            }
            jPushMessage.setBuilderId(1);
            jPushMessage.setThreadId("default");
            jPushMessage.setSound("default");
            jPushMessage.setBadge("+1");
            jPushMessage.setPlatForm("all");
            log.debug("==>jpushMessage:"+ JSON.toJSONString(jPushMessage));
            String result = push(pushUrl,alias, appKey,masterSecret, jPushMessage);
            JSONObject resData = JSONObject.fromObject(result);
            if(resData.containsKey("error")){
                log.info("手机端针对别名为" + alias + "的信息推送失败！");
                JSONObject error = JSONObject.fromObject(resData.get("error"));
                log.info("手机端错误信息为：" + error.get("message").toString());
            }else{
                log.info("手机端针对别名为" + alias + "的信息推送成功！");
            }
            String result2 = push(pushUrl,alias, appPadKey,masterPadSecret, jPushMessage);
            JSONObject resData2 = JSONObject.fromObject(result2);
            if(resData2.containsKey("error")){
                log.info("PAD端针对别名为" + alias + "的信息推送失败！");
                JSONObject error = JSONObject.fromObject(resData.get("error"));
                log.info("PAD端错误信息为：" + error.get("message").toString());
            }else {
                log.info("PAD端针对别名为" + alias + "的信息推送成功！");
            }
        }catch(Exception e){
            log.error("针对别名为" + alias + "的信息推送失败！",e);
        }
    }
    
    /**
     * 组装极光推送专用json串
     * @param alias
     * @param jPushMessage
     * @return json
     */
    private JSONObject generateJson(String alias, JPushMessage jPushMessage){
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//平台
        platform.add("android");
        platform.add("ios");

        //额外参数
        Map<String, Object> extrasMap = new HashMap<>();
        extrasMap.put("action", jPushMessage.getExtras());
        JSONObject notification = new JSONObject();//通知内容
        //android通知内容
        JSONObject android = new JSONObject();
        android.put("alert", jPushMessage.getAlert());
        android.put("title", jPushMessage.getTitle());
        android.put("builder_id", jPushMessage.getBuilderId());
        android.put("extras", extrasMap);
        //ios通知内容
        JSONObject ios = new JSONObject();
        ios.put("alert", jPushMessage.getAlert());
        ios.put("sound", jPushMessage.getSound());
        ios.put("badge", jPushMessage.getBuilderId());
        ios.put("extras", extrasMap);
        notification.put("android", android);
        notification.put("ios", ios);
        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.valueOf(time_to_live));
        options.put("apns_production", apns_production);

        //推送目标
        if(null==alias || alias.equals("all")){
            json.put("audience", alias);
        }else{
            JSONObject audience = new JSONObject();
            audience.put("alias", alias.split(","));
            json.put("audience", audience);
        }

        json.put("platform", platform);
        json.put("notification", notification);
        json.put("options", options);
        return json;
        
    }
    
    /**
     * 推送方法-调用极光API
     * @param reqUrl
     * @param alias
     * @param jPushMessage
     * @return result
     */
    private String push(String reqUrl,String alias, String appKey,String masterSecret, JPushMessage jPushMessage){
        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
        String authorization = "Basic " + base64_auth_string;
        return sendPostRequest(reqUrl,generateJson(alias, jPushMessage).toString(),"UTF-8",authorization);
    }
    
    /**
     * 发送Post请求（json格式）
     * @param reqURL
     * @param data
     * @param encodeCharset
     * @param authorization
     * @return result
     */
    @SuppressWarnings({ "resource" })
    private String sendPostRequest(String reqURL, String data, String encodeCharset,String authorization){
        HttpPost httpPost = new HttpPost(reqURL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String result = "";
        try {
             StringEntity entity = new StringEntity(data, encodeCharset);
             entity.setContentType("application/json");
             httpPost.setEntity(entity);
             httpPost.setHeader("Authorization",authorization.trim());
             response = client.execute(httpPost);
             result = EntityUtils.toString(response.getEntity(), encodeCharset);
        } catch (Exception e) {
            log.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);  
        }finally{
            client.getConnectionManager().shutdown();
        }
        return result;
    }
     /** 
　　　　* BASE64加密工具
　　　　*/
     private String encryptBASE64(String str) {
         byte[] key = str.getBytes();
       BASE64Encoder base64Encoder = new BASE64Encoder();
       String strs = base64Encoder.encodeBuffer(key);
         return strs;
     }
}