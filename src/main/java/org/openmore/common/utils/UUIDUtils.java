package org.openmore.common.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015年9月29日 下午3:16:00 <br/>
 *
 * @author songjiesdnu@163.com
 */
public class UUIDUtils {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}