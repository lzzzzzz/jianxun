package org.openmore.common.polyv;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openmore.cms.entity.User;
import org.openmore.cms.service.UserService;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.MD5Tools;
import org.openmore.common.utils.Sha1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.util.*;

/**保利查询视频信息工具类*/
@Service
@PropertySource("classpath:application.properties")
public class PolyvService {
    private static final Logger logger = LoggerFactory.getLogger(PolyvService.class);

    @Autowired
    private UserService userService;

    @Value("${polyv.config.userId}")
    private String polyvUserId;
    @Value("${polyv.config.appId}")
    private String polyvAppId;
    @Value("${polyv.config.secretkey}")
    private String polyvSecretkey;
    @Value("${polyv.config.readtoken}")
    private String polyvReadtoken;
    @Value("${polyv.config.writetoken}")
    private String polyvWritetoken;
    @Value("${polyv.config.key}")
    private String polyvKey;
    @Value("${polyv.config.iv}")
    private String polyvIv;

    private static final String GET_VIDEO_INFO_URL="http://api.polyv.net/v2/video/%1$s/get-video-msg";

    /**断点续传构造请求地址*/
    private static final String UPLOAD_VIDEO_URL = "https://upload.polyv.net:1081/files/";

    /**保利vid获取上传视频信息*/
    public PolyvVideo getVideo(String vid) {
        String body = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            logger.info("create httppost:" + GET_VIDEO_INFO_URL);
            String url=String.format(GET_VIDEO_INFO_URL, polyvUserId);
            System.out.println("==url:"+url);
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(buildParams(vid)));
            post.addHeader("Accept-Charset", "utf-8");
            HttpResponse response = sendRequest(httpClient, post);
            body = parseResponse(response);
            System.out.println("==>body:"+body);
            if(!StringUtils.isEmpty(body)){
                PolyvResponseData polyvResponseData = JSON.parseObject(body, PolyvResponseData.class);
                if(null!=polyvResponseData && polyvResponseData.getCode() == 200 && polyvResponseData.getStatus().equals("success")){
                    //上传成功
                    PolyvVideo[] polyvVideos = polyvResponseData.getData();
                    if(null==polyvVideos || polyvVideos.length<=0){
                        throw new OpenmoreException("没有获取到视频信息");
                    }
                    return polyvVideos[0];
                }else{
                    throw new OpenmoreException(null!=polyvResponseData?polyvResponseData.getMessage():"视频未上传成功");
                }
            }else{
                throw new OpenmoreException("没有获取到视频信息");
            }
        } catch (OpenmoreException e){
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("send post request failed: {}", e.getMessage());
            throw new OpenmoreException("没有获取到视频信息");
        }
    }

    private static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost)
            throws ClientProtocolException, IOException {
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        return response;
    }

    private static String parseResponse(HttpResponse response) {
        logger.info("get response from http server..");
        HttpEntity entity = response.getEntity();

        logger.info("response status: " + response.getStatusLine());
        Charset charset = ContentType.getOrDefault(entity).getCharset();
        if (charset != null) {
            logger.info(charset.name());
        }

        String body = null;
        try {
            body = EntityUtils.toString(entity, "utf-8");
            logger.info("body " + body);
        } catch (IOException e) {
            logger.warn("{}: cannot parse the entity", e.getMessage());
        }

        return body;
    }

    private List<NameValuePair> buildParams(String vid) throws DigestException{
        List <NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("vid",vid));
        params.add(new BasicNameValuePair("userid", polyvUserId));
        String time=System.currentTimeMillis()+"";
        params.add(new BasicNameValuePair("ptime",time));
        String sign=polyvSign(vid,time);
        params.add(new BasicNameValuePair("sign",sign));
        return params;
    }

    /**创建断点续传请求，返回文件上传链接(错误，暂不使用)*/
    public String getUploaderInfo(String title, String tag, String desc, String ext, String cataid, Long finalLength){
        if(StringUtils.isEmpty(title)){
            throw new OpenmoreException("视频标题不能为空");
        }
        if(null == finalLength || finalLength<=0){
            throw new OpenmoreException("视频长度不能为空");
        }
        List <NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title",title));
        if(StringUtils.isEmpty(tag)){
            params.add(new BasicNameValuePair("tag", tag));
        }
        if(StringUtils.isEmpty(desc)){
            params.add(new BasicNameValuePair("desc",desc));
        }
        params.add(new BasicNameValuePair("ext",ext));
        params.add(new BasicNameValuePair("cataid",cataid));
        String time=System.currentTimeMillis()+"";
        params.add(new BasicNameValuePair("ts",time));
        String sign = MD5Tools.MD5(polyvSecretkey+time).toLowerCase();
        params.add(new BasicNameValuePair("sign",sign));
        String hash = MD5Tools.MD5(time+ polyvUserId).toLowerCase();
        params.add(new BasicNameValuePair("hash",hash));
        params.add(new BasicNameValuePair("userid",polyvUserId));

        String url = UPLOAD_VIDEO_URL;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            logger.info("create httppost:" + GET_VIDEO_INFO_URL);
            System.out.println("==url:"+url);
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(params));
            post.addHeader("Accept-Charset", "utf-8");
            post.addHeader("Final-Length", finalLength+"");
            post.addHeader("userid", polyvUserId);
            HttpResponse response = sendRequest(httpClient, post);
            String location = response.getHeaders("Location")[0].toString();
            System.out.println("==>Location:"+location);
            if(StringUtils.isEmpty(location)){
                throw new OpenmoreException("上传失败");
            }
            return location;
        } catch (OpenmoreException e){
            throw e;
        }catch (Exception e) {
            logger.error("send post request failed: {}", e.getMessage());
            e.printStackTrace();
            throw new OpenmoreException("上传失败");
        }
    }

    /**视频加密播放token验证*/
    public String createToken(HttpServletRequest request, HttpServletResponse response) {
        String postUrl = "http://hls.videocc.net/service/v1/token"; // post 地址
        String vid = ServletRequestUtils.getStringParameter(request, "vid", ""); // vid，不能为空
        long ts = System.currentTimeMillis(); // 时间戳
        int iswxa = ServletRequestUtils.getIntParameter(request, "iswxa", 0); // 是否开启微信授权
        long expires = ServletRequestUtils.getLongParameter(request, "expires", -1); // token有效时长 （单位：秒）
        String viewerIp = PolyvWxSignUtils.geClienttIPAddress(request); // 获取用户IP
        String viewerId = ServletRequestUtils.getStringParameter(request, "wxUserId", ""); // 自定义用户 id，不能为空
        String viewerName = ServletRequestUtils.getStringParameter(request, "viewerName", "");// 用户昵称
        String extraParams = ServletRequestUtils.getStringParameter(request, "extraParams", "");// 自定义参数
        String tokenUrlSign = ServletRequestUtils.getStringParameter(request, "sign", ""); // 请求接口带上的sign
        String openid = ServletRequestUtils.getStringParameter(request, "wxUserId", ""); // 小程序带上的wxUserId
        long tokenUrlTs = ServletRequestUtils.getLongParameter(request, "ts", -1); // 请求接口带上的ts
        String tokenUrlKey = "29af005098pc4f97"; // 接口验证key(固定值)
        logger.debug("==>createToken params:"+ts+"&"+expires+"&"+viewerId+"&"+viewerName+"&"+extraParams+"&"+tokenUrlSign+"&"+openid+"&"+tokenUrlTs);
        if (StringUtils.isEmpty(vid)) {
            return "fail:vid can not be empty";
        }
        if (StringUtils.isEmpty(viewerId)) {
            return "fail:viewerId can not be empty";
        }else{
            //观看者合法身份校验
            User user = userService.getEntityById(viewerId);
            if(null == user){
                return "invalid viewerId";
            }
        }
        /** 接口校验 **/
        String tokenUrlPlain = openid + vid + tokenUrlTs + tokenUrlKey;
        String md5Sign = DigestUtils.md5Hex(tokenUrlPlain).toUpperCase();
        if (!md5Sign.equals(tokenUrlSign)) {
            // sign验证错误，接口不允许返回正确token值
            return "fail:sign invalidate";
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        if (expires > 0) {
            paramMap.put("expires", String.valueOf(expires));
        }
        paramMap.put("iswxa", String.valueOf(iswxa));
        paramMap.put("ts", Long.toString(ts));
        paramMap.put("userId", polyvUserId);
        paramMap.put("videoId", vid);
        paramMap.put("viewerIp", viewerIp);
        paramMap.put("viewerId", viewerId);
        if (!StringUtils.isEmpty(viewerName)) {
            paramMap.put("viewerName", viewerName);
        }
        if (!StringUtils.isEmpty(extraParams)) {
            paramMap.put("extraParams", extraParams);
        }
        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接有序的参数串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(polyvSecretkey);
        for (String key : keyArray) {
            if(StringUtils.isEmpty(paramMap.get(key))){
                paramMap.remove(key);
                continue;
            }
            stringBuilder.append(key).append(paramMap.get(key));
        }
        stringBuilder.append(polyvSecretkey);

        String signSource = stringBuilder.toString();
        // 取32位大写MD5
        String sign = DigestUtils.md5Hex(signSource).toUpperCase();
        paramMap.put("sign", sign);
        String result = PolyvWxSignUtils.sendHttpPost(postUrl, paramMap);
        logger.info(result);
        /** 返回加密数据 **/
        byte[] base64Result = org.apache.commons.codec.binary.Base64.encodeBase64(result.getBytes(StandardCharsets.UTF_8));
        // AES加密
        byte[] key = "2eaf015098dc7f37".getBytes();// AES加密key（固定值）
        byte[] iv = "c9bdd38b60cfc2c7".getBytes();// AES加密iv （固定值）
        byte[] encrypted = AESCryptoUtils.encrypt(base64Result, key, iv);
        String hexEncrypted = Hex.encodeHexString(encrypted);
        return hexEncrypted;
    }


    private String polyvSign(String vid,String ptime)throws DigestException {
        List<String> pa=new ArrayList<String>();
        pa.add("vid");pa.add("userid");pa.add("ptime");
        Collections.sort(pa);
        Map<String,String> pa2=new HashMap<String,String>();
        pa2.put("vid",vid);
        pa2.put("userid", polyvUserId);
        pa2.put("ptime",ptime);
        String str_ensign=pa.get(0)+"="+pa2.get(pa.get(0))+"&"
                +pa.get(1)+"="+pa2.get(pa.get(1))+"&"
                +pa.get(2)+"="+pa2.get(pa.get(2))
                + polyvSecretkey;
        return Sha1.SHA1(str_ensign);
    }

    /**获取加密保利配置*/
    public String getEnCoderConfig() {
        try{
            String plainString = polyvUserId + "," + polyvSecretkey + "," + polyvReadtoken
                    + "," + polyvWritetoken;
            String key = polyvKey;
            String iv = polyvIv;

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = plainString.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            Base64.Encoder encoder = Base64.getEncoder();
            String baseString = encoder.encodeToString(encrypted);
            return baseString;
        }catch (Exception e){
            e.printStackTrace();
            throw new OpenmoreException("保利视频加密出错");
        }
    }
}
