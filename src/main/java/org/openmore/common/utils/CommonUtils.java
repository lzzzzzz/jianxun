package org.openmore.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.CompressionCodecs;
import org.openmore.common.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by michaeltang on 2018/3/22.
 */
public final class CommonUtils {
    private static final String KEY = "ipottery2019";
    private static final int EXPIRE_DAYS = 3650;

    public static final String SCOPE_APP = "app";
    public static final String SCOPE_BACKEND = "backend";

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {
    }


    /**
     * 返回去掉带有手机冠号的手机号
     * 例如：+86_13520664663，返回13520664663
     *
     * @return 去掉带有手机冠号的手机号
     */
    public static String trimPhonePrefixCode(String phoneWithCode) {
        int idx = phoneWithCode.indexOf("_");
        String phoneNum = phoneWithCode;
        if(idx > 0) {
            phoneNum = phoneWithCode.substring(idx + 1);
        }
        return phoneNum;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateHumanReadString(int randomSize) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss_");
        String dateString = formatter.format(currentTime);
        if (randomSize > 0) {
            dateString += CommonUtils.randomNumber(randomSize);
        }
        return dateString;
    }

    /**
     * 将List里的字符串，按照separator拼接
     *
     * @param list
     * @param separator
     * @return
     */
    public static String join(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }


    /**
     * 将手机号处理中中间4位隐藏
     *
     * @param phone
     * @return
     */
    public static String replacePhoneNumber(String phone) {
        if(StringUtils.isEmpty(phone)){
            return null;
        }
        if (StringUtils.isEmpty(phone)) {
            return null;
        }
        if (phone.length() != 11) {
            return phone;
        }
        char[] cArr = phone.toCharArray();
        cArr[3] = '*';
        cArr[4] = '*';
        cArr[5] = '*';
        cArr[6] = '*';
        return new String(cArr);
    }

    /**
     * 将身份证号处理中中间4位隐藏
     *
     * @param idno
     * @return
     */
    public static String replaceIdNumber(String idno) {
        if (StringUtils.isEmpty(idno)) {
            return null;
        }
        if (idno.length() != 18) {
            return idno;
        }
        char[] cArr = idno.toCharArray();
        cArr[6] = '*';
        cArr[7] = '*';
        cArr[8] = '*';
        cArr[9] = '*';
        cArr[10] = '*';
        cArr[11] = '*';
        cArr[12] = '*';
        cArr[13] = '*';
        return new String(cArr);
    }
//
//    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
//    private static final String ALGORITHM_NAME = "md5";
//    private final int hashIterations = 2;
//    public static void encryptPassword(User user) {
//        user.setSalt(randomNumberGenerator.nextBytes().toHex());
//        String newPassword = new SimpleHash(
//                ALGORITHM_NAME,
//                user.getPassword(),
//                ByteSource.Util.bytes(user.getCredentialsSalt()),
//                hashIterations).toHex();
//        user.setPassword(newPassword);
//    }
//

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateString(int randomSize) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        if (randomSize > 0) {
            dateString += CommonUtils.randomNumber(randomSize);
        }
        return dateString;
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }

    /**
     * 获取指定位数的的string随机数，随机范围为A-Z 2-9
     *
     * @param length string的长度
     * @return 指定lenght的随机字符串
     */
    public static String randomString(int length) {
        // 去掉I,O, 0, 1
        String str = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        return randomString(str, length);
    }


    /**
     * 获取指定位数的的string随机数，随机范围为0-9
     *
     * @param length string的长度
     * @return 指定lenght的随机字符串
     */
    public static String randomNumber(int length) {
        // 去掉I,O, 0, 1
        String str = "0123456789012345678901234567890123456789";
        return randomString(str, length);
    }

    /**
     * 获得指定位数的随机数，随机数集合由string指定
     *
     * @param string
     * @param length
     * @return
     */
    public static String randomString(String string, int length) {
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(string.length());
            buf.append(string.charAt(num));
        }
        return buf.toString();
    }

    /**
     * 由userId加密成token
     *
     * @param uid
     * @param deviceToken
     * @param scope
     * @return
     */
    public static String getTokenByUserId(Integer uid, String deviceToken, String scope) {
        if (scope == null) {
            scope = SCOPE_APP;
        }

        logger.debug("deviceToken：" + deviceToken);

        Date expiredData = new Date();
        // 默认过期时间
        expiredData.setTime(System.currentTimeMillis() + EXPIRE_DAYS * 24 * 3600 * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", scope);
        claims.put("device_token", deviceToken);
        claims.put("uid", uid + "");
        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .setClaims(claims)
                .compressWith(CompressionCodecs.DEFLATE)
                .setExpiration(expiredData)
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();

        logger.debug("Token = " + token);

        return token;
    }


    /**
     * 将HttpRequest里的Bearer信息和DeviceTokne进行解析，获得userId信息
     *
     * @param authorization 授权信息
     * @param deviceToken   设备信息，没有时，为none
     * @return
     * @throws InvalidTokenException
     */
    public static int getUserIdFromAuthHeaderInfo(String authorization, String deviceToken) {
        int uid = 0;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            authorization = authorization.substring("Bearer ".length());
            if (deviceToken == null || deviceToken.length() == 0) {
                deviceToken = "none";
            }
            String uidStr = CommonUtils.getUserIdFromToken(authorization, deviceToken);
            uid = uidStr == null ? 0 : Integer.valueOf(uidStr);
        }
        return uid;
    }

    /**
     * 由tokne解密为userId同时检查过期时间
     *
     * @param userToken
     * @return
     * @throws InvalidTokenException
     */
    public static String getUserIdFromToken(String userToken, String deviceToken) throws InvalidTokenException {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(userToken);
            logger.debug("token body：" + jws.getBody());
            String decodeDevToken = (String) (jws.getBody().get("device_token"));
            if (deviceToken == null || !deviceToken.equals(decodeDevToken)) {
                throw new InvalidTokenException("无效的设备token");
            }
            return (String) jws.getBody().get("uid");
        } catch (ExpiredJwtException e) {
            logger.debug("授权已过期，请重新登录");
            throw new InvalidTokenException("授权已过期，请重新登录");
        } catch (InvalidTokenException e) {
            logger.debug("无效的设备token");
            throw e;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new InvalidTokenException("无效token");
        }
    }


    /**
     * 由tokne获得域
     *
     * @param userToken
     * @return
     * @throws InvalidTokenException
     */
    public static String getScopeFromToken(String userToken, String deviceToken) throws InvalidTokenException {
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(userToken);
            logger.debug("token body：" + jws.getBody());
            String decodeDevToken = (String) (jws.getBody().get("device_token"));
            if (deviceToken == null || !deviceToken.equals(decodeDevToken)) {
                throw new InvalidTokenException("无效的设备token");
            }
            String scope = (String) jws.getBody().get("scope");
            return scope;
        } catch (ExpiredJwtException e) {
            logger.debug("授权已过期，请重新登录");
            throw new InvalidTokenException("授权已过期，请重新登录");
        } catch (InvalidTokenException e) {
            logger.debug("无效的设备token");
            throw e;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new InvalidTokenException("无效token");
        }
    }

    /**
     * 自动生成32位的UUid，去掉减号，对应数据库的主键id进行插入用。
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
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


    /**
     * 获得客户端ip地址
     *
     * @return
     */
    public static String getIPAddress(HttpServletRequest request) {
        if(null == request){
            return "127.0.0.1";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    //字符串数组去重
    public static String[] duplicateRemove(String[] str) {
        if (null == str || str.length < 1) {
            return null;
        }
        for (String elementA : str) {
            System.out.print(elementA + " ");
        }
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            if (!list.contains(str[i])) {
                list.add(str[i]);
            }
        }
        String[] newStr = list.toArray(new String[1]);
        return newStr;
    }

    //list去重
    public static List duplicateRemove(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 递归法求最大公约数
     */
    public static int maxCommonDivisor(int m, int n) {
        if (m < n) { // 保证m>n,若m<n,则进行数据交换
            int temp = m;
            m = n;
            n = temp;
        }
        if (m % n == 0) { // 若余数为0,返回最大公约数
            return n;
        } else { // 否则,进行递归,把n赋给m,把余数赋给n
            return maxCommonDivisor(n, m % n);
        }
    }

    /**
     * 计算两数百分比
     */
    public static String getPercent(int x, int total) {
        if (total == 0) {
            return "0%";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位

        numberFormat.setMaximumFractionDigits(2);

        String result = numberFormat.format((float) x / (float) total * 100);
        return result + "%";
    }


    /**
     * 地球半径
     */
    private static double EARTH_RADIUS = 6378138.0;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 指定坐标是否在圆内
     *
     * @param radius 半径，单位/千米
     * @param lat1   点1纬度
     * @param lng1   点1经度
     * @param lat2   点2纬度
     * @param lng2   点2经度
     * @return
     */
    public static boolean isGeoInCircle(double radius, double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        if (s > radius) {
            return false;
        } else {
            return true;
        }
    }
}
