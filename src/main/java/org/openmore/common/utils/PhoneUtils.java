package org.openmore.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneUtils {
    private static Logger logger = LoggerFactory.getLogger(PhoneUtils.class);

    /**
     * 百度api查询手机号码归属地
     */
    public static Map<String, String> parsePhone(String phone) {
        String url = "http://mobsec-dianhua.baidu.com/dianhua_api/open/location?tel=" + phone;
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.debug("==>parse phone content:" + content);
                JSONObject jsonObject = JSONObject.parseObject(content);
                JSONObject responseHeader = jsonObject.getJSONObject("responseHeader");
                if (!responseHeader.get("status").equals(200)) {
                    logger.error("==>parse phone area error:" + phone);
                    return null;
                }
                JSONObject response_baidu = jsonObject.getJSONObject("response");
                JSONObject responsePhone = response_baidu.getJSONObject(phone).getJSONObject("detail");
                String province = responsePhone.get("province").toString();
                JSONArray areas = responsePhone.getJSONArray("area");
                String area = areas.getJSONObject(0).get("city").toString();
                if (StringUtils.isEmpty(province) && StringUtils.isEmpty(area)) {
                    logger.error("==>parse phone area province and area all is enpty error:" + phone);
                    return null;
                }
                Map<String, String> result = new HashMap<>();
                result.put("province", province);
                result.put("area", area);

                return result;
            } else {
                logger.error("==>parse phone area error:" + phone);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("==>parse phone area error:" + phone);
            return null;
        }
    }
}
