package org.openmore.common.polyv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * 小程序授权加密代码示例
 *
 */
@Controller
public class PolyvWxSignUtils {

    @Value("polyv.config.secretkey")
    private String secretKey;
    @Value("polyv.config.userId")
    private String userId;
    @Value("polyv.config.key")
    private String aesKey;
    @Value("polyv.config.key")
    private String aesIv;
     
    private static final Logger logger = LoggerFactory.getLogger(PolyvWxSignUtils.class);
     
    /**
     * create token demo
     */
    /*public String createToken(HttpServletRequest request, String vid, String viewerId) {
        String postUrl = "http://hls.videocc.net/service/v1/token"; // post 地址
        String secretkey = "your secretkey"; // secretkey
        String uid = "87365d03dc"; // uid
        long ts = System.currentTimeMillis(); // 时间戳
        int iswxa = 1; // 是否开启微信授权
        long expires = 600; // token有效时长 （单位：秒）
        String viewerIp = this.geClienttIPAddress(request);
        Map<String, String> paramMap = new HashMap<String, String>();
        if (expires > 0) {
            paramMap.put("expires", String.valueOf(expires));
        }
        paramMap.put("iswxa", String.valueOf(iswxa));
        paramMap.put("ts", Long.toString(ts));
        paramMap.put("userId", uid);
        paramMap.put("videoId", vid);
        paramMap.put("viewerIp", viewerIp);
        paramMap.put("viewerId", viewerId);

        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接有序的参数串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(secretkey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        stringBuilder.append(secretkey);
        String signSource = stringBuilder.toString();
        // 取32位大写MD5
        String sign = DigestUtils.md5Hex(signSource).toUpperCase();
        paramMap.put("sign", sign);
        String result = this.sendHttpPost(postUrl, paramMap);
        logger.info(result);
        *//** 返回加密数据 **//*
        byte[] base64Result = Base64.encodeBase64(result.getBytes(StandardCharsets.UTF_8));
        // AES加密
        byte[] key = aesKey.getBytes();// AES加密key（固定值）
        byte[] iv = aesIv.getBytes();// AES加密iv （固定值）
        byte[] encrypted = AESCryptoUtils.encrypt(base64Result, key, iv);
        String hexEncrypted = Hex.encodeHexString(encrypted);
        return hexEncrypted;
    }*/



    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String geClienttIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
                ip = (ip.split(",")[0]).trim();
            }
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
     
    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sendHttpPost(httpPost);
    }
     
    /**
     * 发送Post请求
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {
        HttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            CloseableHttpClient hc = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000)
                    .build();// 设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            // 创建默认的httpClient实例.
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = hc.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return responseContent;
    }
}