package org.openmore.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lock {

    public static synchronized String getNumber(){
        DateFormat df = new SimpleDateFormat("ssMMSSSmmyHHdd");
        return df.format(new Date());
    }

    /**
     * McihaelTang add 06.08
     * 生成带有时间的随机订单号
     * @param prefix
     * @return
     */
    public static String getOutTradeNumber(String prefix){
        if(prefix == null){
            prefix = "";
        }
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return prefix + df.format(new Date()) + Utils.randomNumber(6);
    }


    public static synchronized String getPreNumber(String maxNum) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String newNumDate = df.format(new Date());
        String oldNumDate = "";
        if (StringUtils.isNotBlank(maxNum))
            oldNumDate = maxNum.substring(0, 8);
        if (oldNumDate.equals(newNumDate))
            return oldNumDate + String.valueOf(Integer.parseInt(maxNum.substring(8, maxNum.length())) + 1);
        return newNumDate + "10000";
    }
}