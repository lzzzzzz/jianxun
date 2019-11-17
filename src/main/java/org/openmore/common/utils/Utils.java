package org.openmore.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


	public static Map<String, Object> memCache = new HashMap<>();

	/**
	 * 清除所有缓存
	 */
	public void flushMemCache(){
		memCache.clear();
	}


	public static boolean isType(Field field, String basicTypeName) {
		if("int".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.Integer") || field.getGenericType().toString().equals(
					"class java.lang.int");
		} else if ("float".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.Float") || field.getGenericType().toString().equals(
					"float");
		} else if ("long".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.Long") || field.getGenericType().toString().equals(
					"long");
		} else if ("byte".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.Byte") || field.getGenericType().toString().equals(
					"byte");
		} else if ("short".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.Short") || field.getGenericType().toString().equals(
					"short");
		} else if ("double".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.Double") || field.getGenericType().toString().equals(
					"double");
		} else if ("bigInt".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.BigInteger");
		} else if ("bigDecimal".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.math.BigDecimal");
		} else if ("string".equals(basicTypeName)) {
			return field.getGenericType().toString().equals(
					"class java.lang.String");
		}
		return false;
	}

	static public <T>T deepClone(T o){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(o);
			byte[] buf = out.toByteArray();
			ByteArrayInputStream in = new ByteArrayInputStream(buf);
			ObjectInputStream oio = new ObjectInputStream(in);
			T copy = (T) oio.readObject();
			return copy;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;


	}

	static public String joinWithToken(Collection<String> ss, String token){
		List<String> idss = new ArrayList<String>();
		for(String s:ss){
			idss.add(token + s + token);
		}
		return StringUtils.join(idss, ',');
	}
	static public String joinWithToken(String[] ss, String token){
		List<String> idss = new ArrayList<String>();
		for(String s:ss){
			idss.add(token + s + token);
		}
		return StringUtils.join(idss, ',');
	}

	public static List<String> formatFileSize(File f) throws Exception{//取得文件大小
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s= fis.available();
        } else {
        	throw new RuntimeException("文件不存在");
        }
        return convertFileSize(s);
    }

	private static List<String> convertFileSize(long fileS) {//转换文件大小
		List<String> rst = new ArrayList<String>();
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
        	rst.add(df.format((double) fileS));
        	rst.add("B");
        } else if (fileS < 1048576) {
        	rst.add(df.format((double) fileS / 1024));
        	rst.add("K");
        } else if (fileS < 1073741824) {
        	rst.add(df.format((double) fileS / 1048576));
        	rst.add("M");
        } else {
        	rst.add(df.format((double) fileS / 1073741824));
        	rst.add("G");
        }
        return rst;
    }


	/**
	 * 获取指定位数的的string随机数，随机范围为A-Z 2-9
	 * @param length string的长度
	 * @return 指定lenght的随机字符串
	 */
	public static String randomString(int length){
		// 去掉I,O, 0, 1
		String str = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
		return randomString(str, length);
	}

	/**
	 * 获取指定位数的的string随机数，随机范围为0-9
	 * @param length string的长度
	 * @return 指定lenght的随机字符串
	 */
	public static String randomNumber(int length){
		// 去掉I,O, 0, 1
		String str = "0123456789012345678901234567890123456789";
		return randomString(str, length);
	}

	/**
	 * 获得指定位数的随机数，随机数集合由string指定
	 * @param string
	 * @param length
	 * @return
	 */
	public static String randomString(String string, int length){
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(string.length());
			buf.append(string.charAt(num));
		}
		return buf.toString();
	}

	/**
	 * 下划线转驼峰
	 */
	private static Pattern linePattern = Pattern.compile("_(\\w)");
	public static String lineToHump(String str) {
//        str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/***
	 * 驼峰命名转为下划线命名
	 *
	 * @param para 驼峰命名的字符串
	 */

	public static String humpToUnderline(String para) {
		StringBuilder sb = new StringBuilder(para);
		int temp = 0;
		for (int i = 1; i < para.length(); i++) {
			if (Character.isUpperCase(para.charAt(i))) {
				sb.insert(i + temp, "_");
				temp += 1;
			}
		}
		return sb.toString().toLowerCase();
	}
}