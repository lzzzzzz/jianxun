package org.openmore.common.storage;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openmore.cms.dto.api.ResourcesDto;
import org.openmore.cms.service.ResourcesService;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 阿里云对象存储OSS
 * 文档: https://help.aliyun.com/document_detail/32008.html?spm=5176.doc32008.3.3.3YkvaP
 */
@Service
@PropertySource("classpath:application.properties")
public class AliyunCloudStorageService implements CloudStorageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ResourcesService resourcesService;

    private OSSClient ossClient;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    @Value("${aliyun.oss.urlDomain}")
    private String urlDomain;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.callbackUrl}")
    private String callbackUrl;

    // 前面不能带有/
    private final String ossPrefix = "upload/";

    @Override
    public String upload(byte[] data, String fileKey) throws Exception {
        return upload(new ByteArrayInputStream(data), fileKey);
    }

    private OSSClient getOssClient() {
        if(ossClient == null) {
            return ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
        return ossClient;
    }

    public void deleteFile(String objName) {
        try {
            getOssClient().deleteObject(bucket, objName);
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获得直传Key
     * @return
     */
    public Map<String, String> directUpload(){
        try {
            long expireTime = 180;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, ossPrefix);

            String postPolicy = getOssClient().generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = getOssClient().calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", ossPrefix);
            respMap.put("host", urlDomain);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            respMap.put("success_action_status", "200");

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);
            return respMap;
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    private boolean doCheck(String content, byte[] sign, String publicKey) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(content.getBytes());
        boolean bverify = signature.verify(sign);
        return bverify;
    }


    /**
     * 获取public key
     *
     * @param url
     * @return
     */
    @SuppressWarnings({ "finally" })
    public String executeGet(String url) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            DefaultHttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
        } catch (Exception e) {
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }

    public ResourcesDto uploadCallback(String autorizationInput, String pubKeyInput, String queryString, String uri,
                                       String filename, String size, String mimeType, String width, String height
    ) throws Exception{
//        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
//        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
//        String pubKeyAddr = new String(pubKey);
//        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
//                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
//            System.out.println("pub key addr must be oss addrss");
//            throw new OpenmoreException("pub key addr must be oss addrss");
//        }
//        String retString = executeGet(pubKeyAddr);
//        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
//        retString = retString.replace("-----END PUBLIC KEY-----", "");
//        String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
//        String authStr = decodeUri;
//        if (queryString != null && !queryString.equals("")) {
//            authStr += "?" + queryString;
//        }
//        authStr += "\n" + ossCallbackBody;
//        logger.debug(authStr);
//        logger.debug(ossCallbackBody);
//        doCheck(authStr, authorization, retString);
//        String url = "https://" + bucket + "." + endpoint + "/";
//        if (!StringUtils.isEmpty(url)) {
            ResourcesDto resources1 = new ResourcesDto();
            resources1.setSize(size);
            resources1.setKey(filename);
            resources1.setName(filename);
            resources1.setUrl(urlDomain + "/" + filename);
            resources1.setType(resourcesService.getResourceType(filename));
            return resourcesService.insert(resources1);
//        }
//        return null;
    }

    /**
     * 流上传（可上传文件、字符串、网络流等流式数据）
     */
    @Override
    public String upload(InputStream inputStream, String fileKey) {
        if (getOssClient().doesBucketExist(bucket)) {
            System.out.println("您已经创建Bucket：" + bucket + "。");
        } else {
            System.out.println("您的Bucket不存在，创建Bucket：" + bucket + "。");
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            //ossClient.createBucket(bucketName);
            logger.error("==>您的Bucket不存在:" + bucket);
            throw new OpenmoreException(4001, "存储出错");
        }
        try {
            getOssClient().putObject(bucket, fileKey, inputStream);
        } catch (Exception e) {
//            ossClient.shutdown();
            throw new OpenmoreException(4001, "文件上传出错");
        } finally {
//            ossClient.shutdown();
        }
        /*Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucket, fileKey, expiration).toString();*/
        return fileKey;
    }

    /**
     * 文件上传
     */
    @Override
    public String upload(MultipartFile multiFile, String fileKey) {
        if (getOssClient().doesBucketExist(bucket)) {
//            System.out.println("您已经创建Bucket：" + bucket + "。");
        } else {
            System.out.println("您的Bucket不存在，创建Bucket：" + bucket + "。");
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            //getOssClient().createBucket(bucketName);
            logger.error("==>您的Bucket不存在:" + bucket);
            throw new OpenmoreException(4001, "存储出错:Bucket不存在");
        }
        try {
            getOssClient().putObject(bucket, fileKey, multiFile.getInputStream());
        } catch (ClientException e) {
//            getOssClient().shutdown();
            throw new OpenmoreException(4001, "文件上传出错:" + e.getErrorMessage());
        } catch (Exception e){
            throw new OpenmoreException(4001, "文件上传出错:" + e.getMessage());
        } finally {
//            getOssClient().shutdown();
        }
        /*Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = getOssClient().generatePresignedUrl(bucket, fileKey, expiration).toString();*/
        return fileKey;
    }

    /**
     * 进度条上传
     */
    public String progressUpload(MultipartFile multiFile, String fileKey, PutObjectProgressListener listener) {
        try {
            // 带进度条的上传。
            getOssClient().putObject(new PutObjectRequest(bucket, fileKey, multiFile.getInputStream()).
                    <PutObjectRequest>withProgressListener(listener));
        } catch (Exception e) {
//            getOssClient().shutdown();
            logger.error("==>进度条上传文件出错:" + e.getMessage());
            throw new OpenmoreException(4001, "上传失败");
        } finally {
//            getOssClient().shutdown();
        }
        /*Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        String url = getOssClient().generatePresignedUrl(bucket, fileKey, expiration).toString();*/
        return fileKey;
    }

    /**
     * 附件文件下载
     */
    public void download(HttpServletResponse response, String fileKey, String fileName) {
        //OSSClient client = new OSSClient(env.getProperty("aliyun.oss.endpoint"), env.getProperty("aliyun.oss.accessKeyId"), env.getProperty("aliyun.oss.accessKeySecret"));
        try {
            // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            OSSObject ossObject = getOssClient().getObject(bucket, fileKey);

            // 读去Object内容  返回
            BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
            //System.out.println(ossObject.getObjectContent().toString());

            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            //通知浏览器以附件形式下载
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            //BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(new File("f:\\a.txt")));

            byte[] car = new byte[1024];
            int L = 0;
            while ((L = in.read(car)) != -1) {
                out.write(car, 0, L);

            }
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
//            getOssClient().shutdown();
            logger.error("==>下载文件出错:" + e.getMessage());
            throw new OpenmoreException(4001, "下载文件失败");
        } finally {
//            getOssClient().shutdown();
        }
    }

    /**
     * get save file name
     */
    public String getFileKey(String fileName) {
        String e = DateUtil.dateToString(DateUtil.DATE_PATTERN, new Date());
        UUID uuid = UUID.randomUUID();
        String osskey = uuid.toString().replace("-", "");
        String[] split = fileName.split("\\.");
        osskey += "." + split[split.length - 1];
        return "qpf/" + e + "/" + osskey;
    }
}
