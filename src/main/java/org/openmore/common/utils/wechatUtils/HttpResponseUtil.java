package org.openmore.common.utils.wechatUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class HttpResponseUtil {

    public static String parseResponse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        Charset charset = ContentType.getOrDefault(entity).getCharset();
        String result = null;
        result = EntityUtils.toString(entity, "utf-8");
        return result;
    }

}
