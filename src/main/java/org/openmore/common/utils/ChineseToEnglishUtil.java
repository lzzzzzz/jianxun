package org.openmore.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by michaeltang on 2018/8/29.
 */
public class ChineseToEnglishUtil {
    public static String toHanyuPinyin(String chineseLanguage){
        if (chineseLanguage.contains("/")) {
            chineseLanguage = chineseLanguage.replace('/', '_');
        }

        char[] clChars = chineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = initHanyuPinyinOutputFormat();
        try {
            for (int i = 0; i < clChars.length; i++){
                if (String.valueOf(clChars[i]).matches("[\u4e00-\u9fa5]+")){
                    if (i > 0)
                        hanyupinyin += "_";
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(clChars[i], defaultFormat)[0];
                    if ((i + 1) < clChars.length)
                        if (!String.valueOf(clChars[i + 1]).matches("[\u4e00-\u9fa5]+"))
                            hanyupinyin += "_";
                } else
                    hanyupinyin += clChars[i];
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return hanyupinyin.toUpperCase();
    }

    public static String firstLetterPinYin(String chineseLanguage){
        HanyuPinyinOutputFormat defaultFormat = initHanyuPinyinOutputFormat();
        String firstLetter = "";
        char[] clChars = chineseLanguage.trim().toCharArray();
        try {
            if (String.valueOf(clChars[0]).matches("[\u4e00-\u9fa5]")) {
                String pinyin = PinyinHelper.toHanyuPinyinStringArray(clChars[0], defaultFormat)[0];
                if (StringUtils.isNotBlank(pinyin))
                    firstLetter =  pinyin.substring(0,1);
            }
            else
                firstLetter =  String.valueOf(clChars[0]);
        } catch (BadHanyuPinyinOutputFormatCombination e){
            e.printStackTrace();
        }
        return firstLetter;
    }

    private static HanyuPinyinOutputFormat initHanyuPinyinOutputFormat(){
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
        return defaultFormat;
    }
}
